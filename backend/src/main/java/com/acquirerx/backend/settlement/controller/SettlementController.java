package com.acquirerx.backend.settlement.controller;

import com.acquirerx.backend.settlement.dto.AdjustmentDTO;
import com.acquirerx.backend.settlement.entity.Adjustment;
import com.acquirerx.backend.settlement.entity.Payout;
import com.acquirerx.backend.settlement.entity.SettlementBatch;
import com.acquirerx.backend.settlement.service.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entry point for triggering the settlement process.
 * In production, this would be called by a Scheduler at EOD.
 */
@RestController
@RequestMapping("/api/v1/settlement")
public class SettlementController {

    @Autowired
    private SettlementService settlementService;

    /**
     * POST endpoint to run settlement for a specific merchant.
     * Use Postman to pass 'sales' and 'fees' as parameters.
     */
    @PostMapping("/run/{merchantId}")
    public SettlementBatch run(@PathVariable("merchantId") Long merchantId,
                               @RequestParam("sales") BigDecimal sales,
                               @RequestParam("fees") BigDecimal fees) {
        return settlementService.processSettlement(merchantId, sales, fees);
    }

    // --- Batch endpoints ---

    @GetMapping("/batches")
    public ResponseEntity<List<SettlementBatch>> getAllBatches() {
        return ResponseEntity.ok(settlementService.getAllBatches());
    }

    @GetMapping("/batches/{batchId}")
    public ResponseEntity<SettlementBatch> getBatchById(@PathVariable("batchId") Long batchId) {
        return ResponseEntity.ok(settlementService.getBatchById(batchId));
    }

    @GetMapping("/batches/merchant/{merchantId}")
    public ResponseEntity<List<SettlementBatch>> getBatchesByMerchant(@PathVariable("merchantId") Long merchantId) {
        return ResponseEntity.ok(settlementService.getBatchesByMerchant(merchantId));
    }

    // --- Payout endpoints ---

    @GetMapping("/payouts")
    public ResponseEntity<List<Payout>> getAllPayouts() {
        return ResponseEntity.ok(settlementService.getAllPayouts());
    }

    @GetMapping("/payouts/{payoutId}")
    public ResponseEntity<Payout> getPayoutById(@PathVariable("payoutId") Long payoutId) {
        return ResponseEntity.ok(settlementService.getPayoutById(payoutId));
    }

    @PutMapping("/payouts/{payoutId}/status")
    public ResponseEntity<Payout> updatePayoutStatus(@PathVariable("payoutId") Long payoutId,
                                                     @RequestParam("status") String status) {
        return ResponseEntity.ok(settlementService.updatePayoutStatus(payoutId, status));
    }

    // --- Adjustment endpoints ---

    @PostMapping("/adjustments")
    public ResponseEntity<Adjustment> createAdjustment(@RequestBody AdjustmentDTO dto) {
        return ResponseEntity.ok(settlementService.createAdjustment(dto));
    }

    @GetMapping("/adjustments/merchant/{merchantId}")
    public ResponseEntity<List<Adjustment>> getAdjustmentsByMerchant(@PathVariable("merchantId") Long merchantId) {
        return ResponseEntity.ok(settlementService.getAdjustmentsByMerchant(merchantId));
    }

    @PutMapping("/adjustments/{adjId}/status")
    public ResponseEntity<Adjustment> updateAdjustmentStatus(@PathVariable("adjId") Long adjId,
                                                             @RequestParam("status") String status) {
        return ResponseEntity.ok(settlementService.updateAdjustmentStatus(adjId, status));
    }
}