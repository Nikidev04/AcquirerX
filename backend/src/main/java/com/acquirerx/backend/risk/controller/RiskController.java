package com.acquirerx.backend.risk.controller;

import com.acquirerx.backend.risk.dto.BlacklistDTO;
import com.acquirerx.backend.risk.dto.RiskRuleDTO;
import com.acquirerx.backend.risk.entity.Blacklist;
import com.acquirerx.backend.risk.entity.RiskEvent;
import com.acquirerx.backend.risk.entity.RiskRule;
import com.acquirerx.backend.risk.service.RiskEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk")
public class RiskController {

    private final RiskEngineService riskEngineService;

    public RiskController(RiskEngineService riskEngineService) {
        this.riskEngineService = riskEngineService;
    }

    // ── Risk Rules ──────────────────────────────────────────────────────────

    @PostMapping("/rules")
    public ResponseEntity<RiskRule> createRule(@RequestBody RiskRuleDTO dto) {
        RiskRule saved = riskEngineService.createRule(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/rules")
    public ResponseEntity<List<RiskRule>> getAllRules() {
        return ResponseEntity.ok(riskEngineService.getAllRules());
    }

    @GetMapping("/rules/{ruleId}")
    public ResponseEntity<RiskRule> getRuleById(@PathVariable("ruleId") Long ruleId) {
        return ResponseEntity.ok(riskEngineService.getRuleById(ruleId));
    }

    @PutMapping("/rules/{ruleId}")
    public ResponseEntity<RiskRule> updateRule(@PathVariable("ruleId") Long ruleId,
                                               @RequestBody RiskRuleDTO dto) {
        return ResponseEntity.ok(riskEngineService.updateRule(ruleId, dto));
    }

    @DeleteMapping("/rules/{ruleId}")
    public ResponseEntity<String> deleteRule(@PathVariable("ruleId") Long ruleId) {
        riskEngineService.deleteRule(ruleId);
        return ResponseEntity.ok("Rule deactivated");
    }

    // ── Risk Evaluation ─────────────────────────────────────────────────────

    @PostMapping("/evaluate")
    public ResponseEntity<RiskEvent> evaluateTransaction(@RequestParam("txnId") Long txnId,
                                                         @RequestParam("amount") Double amount) {
        return ResponseEntity.ok(riskEngineService.evaluateTransaction(txnId, amount));
    }

    // ── Risk Events ─────────────────────────────────────────────────────────

    @GetMapping("/events")
    public ResponseEntity<List<RiskEvent>> getEvents(
            @RequestParam(value = "result", required = false) String result) {
        if (result != null && !result.isBlank()) {
            return ResponseEntity.ok(riskEngineService.getEventsByResult(result));
        }
        return ResponseEntity.ok(riskEngineService.getAllEvents());
    }

    @GetMapping("/events/txn/{txnId}")
    public ResponseEntity<List<RiskEvent>> getEventsByTxnId(@PathVariable("txnId") Long txnId) {
        return ResponseEntity.ok(riskEngineService.getEventsByTxnId(txnId));
    }

    // ── Blacklist ────────────────────────────────────────────────────────────

    @PostMapping("/blacklist")
    public ResponseEntity<Blacklist> addToBlacklist(@RequestBody BlacklistDTO dto) {
        return ResponseEntity.ok(riskEngineService.addToBlacklist(dto));
    }

    @GetMapping("/blacklist")
    public ResponseEntity<List<Blacklist>> getBlacklist(
            @RequestParam(value = "type", required = false) String type) {
        if (type != null && !type.isBlank()) {
            return ResponseEntity.ok(riskEngineService.getBlacklistByType(type));
        }
        return ResponseEntity.ok(riskEngineService.getAllBlacklist());
    }

    @DeleteMapping("/blacklist/{blacklistId}")
    public ResponseEntity<String> removeFromBlacklist(@PathVariable("blacklistId") Long blacklistId) {
        riskEngineService.removeFromBlacklist(blacklistId);
        return ResponseEntity.ok("Removed from blacklist");
    }
}
