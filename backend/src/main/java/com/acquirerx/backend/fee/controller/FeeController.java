package com.acquirerx.backend.fee.controller;

import com.acquirerx.backend.fee.dto.FeeRuleDTO;
import com.acquirerx.backend.fee.entity.FeeRule;
import com.acquirerx.backend.fee.entity.Txn;
import com.acquirerx.backend.fee.repository.FeeRuleRepository;
import com.acquirerx.backend.fee.service.FeeEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
public class FeeController {

    private final FeeEngineService feeEngineService;
    private final FeeRuleRepository feeRuleRepository;

    public FeeController(FeeEngineService feeEngineService, FeeRuleRepository feeRuleRepository) {
        this.feeEngineService = feeEngineService;
        this.feeRuleRepository = feeRuleRepository;
    }

    @PostMapping("/addRule")
    public String addRule(@RequestBody FeeRuleDTO dto) {
        FeeRule rule = new FeeRule();

        rule.setRuleType(dto.getRuleType());
        rule.setCriteriaJson(dto.getCriteriaJson());
        rule.setRatePct(dto.getRatePct());
        rule.setFlatFee(dto.getFlatFee());
        rule.setEffectiveFrom(dto.getEffectiveFrom());
        rule.setEffectiveTo(dto.getEffectiveTo());
        rule.setStatus("ACTIVE");

        feeRuleRepository.save(rule);
        return "Rule Saved!";
    }

    @PostMapping("/calculate")
    public Txn calculate(@RequestBody Txn txn) {
        return feeEngineService.computeFees(txn);
    }

    @GetMapping("/rules")
    public ResponseEntity<List<FeeRule>> getAllRules() {
        return ResponseEntity.ok(feeEngineService.getAllRules());
    }

    @GetMapping("/rules/{ruleId}")
    public ResponseEntity<FeeRule> getRuleById(@PathVariable("ruleId") Long ruleId) {
        return ResponseEntity.ok(feeEngineService.getRuleById(ruleId));
    }

    @PutMapping("/rules/{ruleId}")
    public ResponseEntity<FeeRule> updateRule(@PathVariable("ruleId") Long ruleId, @RequestBody FeeRuleDTO dto) {
        return ResponseEntity.ok(feeEngineService.updateRule(ruleId, dto));
    }

    @DeleteMapping("/rules/{ruleId}")
    public ResponseEntity<String> deactivateRule(@PathVariable("ruleId") Long ruleId) {
        feeEngineService.deactivateRule(ruleId);
        return ResponseEntity.ok("Rule deactivated");
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Txn>> getAllTransactions() {
        return ResponseEntity.ok(feeEngineService.getAllTransactions());
    }

    @GetMapping("/transactions/{txnId}")
    public ResponseEntity<Txn> getTransactionById(@PathVariable("txnId") Long txnId) {
        return ResponseEntity.ok(feeEngineService.getTransactionById(txnId));
    }
}
