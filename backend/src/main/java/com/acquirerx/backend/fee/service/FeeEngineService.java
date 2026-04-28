package com.acquirerx.backend.fee.service;

import com.acquirerx.backend.exception.ResourceNotFoundException;
import com.acquirerx.backend.fee.dto.FeeRuleDTO;
import com.acquirerx.backend.fee.entity.FeeRule;
import com.acquirerx.backend.fee.entity.Txn;
import com.acquirerx.backend.fee.repository.FeeRuleRepository;
import com.acquirerx.backend.fee.repository.TxnRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FeeEngineService {

    private final FeeRuleRepository feeRuleRepository;
    private final TxnRepository txnRepository;

    public FeeEngineService(FeeRuleRepository feeRuleRepository, TxnRepository txnRepository) {
        this.feeRuleRepository = feeRuleRepository;
        this.txnRepository = txnRepository;
    }

    // Step 1: Find rule
    public FeeRule findApplicableRule(Txn txn) {
        List<FeeRule> rules = feeRuleRepository.findAllActiveRules();
        LocalDate today = LocalDate.now();

        for (FeeRule rule : rules) {
            boolean validDate = !today.isBefore(rule.getEffectiveFrom())
                    && !today.isAfter(rule.getEffectiveTo());

            if (validDate) {
                return rule; // simplified for demo
            }
        }
        return null;
    }

    private Double pct(Double amount, Double pct) {
        if (pct == null) return 0.0;
        return (amount * pct) / 100.0;
    }

    public Txn computeFees(Txn txn) {
        FeeRule rule = findApplicableRule(txn);

        if (rule == null) {
            throw new RuntimeException("No fee rule found");
        }

        Double txnAmount = txn.getAmount();
        if (txnAmount == null) {
            throw new IllegalArgumentException("Transaction amount is required");
        }

        double amount = txnAmount;
        Double ruleRate = rule.getRatePct();
        Double ruleFlatFee = rule.getFlatFee();

        double interchange = pct(amount, ruleRate) + (ruleFlatFee == null ? 0.0 : ruleFlatFee);

        double schemeFee = pct(amount, ruleRate);
        double mdr = pct(amount, ruleRate);
        double markup = pct(amount, ruleRate);

        double merchantNet = amount - (mdr + schemeFee);

        txn.setInterchangeFee(interchange);
        txn.setSchemeFee(schemeFee);
        txn.setAcquirerMarkup(markup);
        txn.setNetMerchantAmount(merchantNet);
        txn.setStatus("CAPTURED");

        return txn;
    }

    public List<FeeRule> getAllRules() {
        return feeRuleRepository.findAll();
    }

    public FeeRule getRuleById(Long id) {
        return feeRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fee rule not found: " + id));
    }

    public FeeRule updateRule(Long id, FeeRuleDTO dto) {
        FeeRule rule = feeRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fee rule not found: " + id));
        rule.setRuleType(dto.getRuleType());
        rule.setCriteriaJson(dto.getCriteriaJson());
        rule.setRatePct(dto.getRatePct());
        rule.setFlatFee(dto.getFlatFee());
        rule.setEffectiveFrom(dto.getEffectiveFrom());
        rule.setEffectiveTo(dto.getEffectiveTo());
        return feeRuleRepository.save(rule);
    }

    public void deactivateRule(Long id) {
        FeeRule rule = feeRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fee rule not found: " + id));
        rule.setStatus("INACTIVE");
        feeRuleRepository.save(rule);
    }

    public List<Txn> getAllTransactions() {
        return txnRepository.findAll();
    }

    public Txn getTransactionById(Long id) {
        return txnRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + id));
    }
}
