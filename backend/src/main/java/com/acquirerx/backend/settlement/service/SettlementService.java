package com.acquirerx.backend.settlement.service;

import com.acquirerx.backend.exception.ResourceNotFoundException;
import com.acquirerx.backend.settlement.dto.AdjustmentDTO;
import com.acquirerx.backend.settlement.entity.*;
import com.acquirerx.backend.settlement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class SettlementService {

    @Autowired private SettlementBatchRepository batchRepo;
    @Autowired private AdjustmentRepository adjRepo;
    @Autowired private PayoutRepository payoutRepo;

    /**
     * Core logic to calculate Net Payout and recover outstanding debts.
     */
    @Transactional
    public SettlementBatch processSettlement(Long merchantId, BigDecimal sales, BigDecimal fees) {

        // 1. DATA FETCH: Find all "Pending" debts (like the $900 overcharge)
        List<Adjustment> pendingAdjs = adjRepo.findByMerchantIdAndStatus(merchantId, "PENDING");

        // 2. DATA AGGREGATION: Sum up all debts using Stream API
        BigDecimal totalAdj = pendingAdjs.stream()
                .map(Adjustment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. MATH: Net Payout = (Sales - Fees) + Adjustments (Note: Adjustments are negative)
        // Example: ($1000 - $20) + (-$900) = $80
        BigDecimal net = sales.subtract(fees).add(totalAdj);

        // 4. BATCH CREATION: Prepare the daily summary record
        SettlementBatch batch = new SettlementBatch();
        batch.setMerchantId(merchantId);
        batch.setGrossAmount(sales);
        batch.setTotalFees(fees);
        batch.setAdjustmentAmount(totalAdj);
        batch.setPostedDate(LocalDate.now());

        // 5. DEBT RECOVERY LOGIC:
        if (net.compareTo(BigDecimal.ZERO) < 0) {
            // Merchant owes more than they earned; set payout to 0 and carry debt forward
            batch.setNetAmount(BigDecimal.ZERO);
            batch.setStatus("DEBT_CARRIED_FORWARD");
        } else {
            // Merchant has cleared their debt; calculate final pay and close adjustments
            batch.setNetAmount(net);
            batch.setStatus("READY");
            // Mark the debts as SETTLED so they aren't deducted again tomorrow
            pendingAdjs.forEach(a -> a.setStatus("SETTLED"));
        }

        batch = batchRepo.save(batch);

        // 6. PAYOUT GENERATION: Create a payment instruction if net > 0
        if (batch.getNetAmount().compareTo(BigDecimal.ZERO) > 0) {
            Payout payout = new Payout();
            payout.setSettleBatchId(batch.getSettleBatchId());
            payout.setAmount(batch.getNetAmount());
            payout.setPayoutDate(LocalDate.now());
            payout.setStatus("INITIATED");
            payoutRepo.save(payout);
        }

        return batch;
    }

    // --- Batch methods ---

    public List<SettlementBatch> getAllBatches() {
        return batchRepo.findAll();
    }

    public SettlementBatch getBatchById(Long id) {
        return batchRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found: " + id));
    }

    public List<SettlementBatch> getBatchesByMerchant(Long merchantId) {
        return batchRepo.findByMerchantId(merchantId);
    }

    // --- Payout methods ---

    public List<Payout> getAllPayouts() {
        return payoutRepo.findAll();
    }

    public Payout getPayoutById(Long id) {
        return payoutRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payout not found: " + id));
    }

    public Payout updatePayoutStatus(Long id, String status) {
        Payout payout = getPayoutById(id);
        payout.setStatus(status);
        return payoutRepo.save(payout);
    }

    // --- Adjustment methods ---

    public Adjustment createAdjustment(AdjustmentDTO dto) {
        Adjustment adjustment = new Adjustment();
        adjustment.setMerchantId(dto.getMerchantId());
        adjustment.setTxnId(dto.getTxnId());
        adjustment.setAmount(dto.getAmount());
        adjustment.setReason(dto.getReason());
        adjustment.setStatus("PENDING");
        adjustment.setPostedDate(LocalDate.now());
        return adjRepo.save(adjustment);
    }

    public List<Adjustment> getAdjustmentsByMerchant(Long merchantId) {
        return adjRepo.findByMerchantId(merchantId);
    }

    public Adjustment updateAdjustmentStatus(Long id, String status) {
        Adjustment adjustment = adjRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adjustment not found: " + id));
        adjustment.setStatus(status);
        return adjRepo.save(adjustment);
    }
}