package com.acquirerx.backend.risk.service;

import com.acquirerx.backend.exception.ResourceNotFoundException;
import com.acquirerx.backend.risk.dto.BlacklistDTO;
import com.acquirerx.backend.risk.dto.RiskRuleDTO;
import com.acquirerx.backend.risk.entity.Blacklist;
import com.acquirerx.backend.risk.entity.RiskEvent;
import com.acquirerx.backend.risk.entity.RiskRule;
import com.acquirerx.backend.risk.repository.BlacklistRepository;
import com.acquirerx.backend.risk.repository.RiskEventRepository;
import com.acquirerx.backend.risk.repository.RiskRuleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RiskEngineService {

    private final RiskRuleRepository riskRuleRepository;
    private final RiskEventRepository riskEventRepository;
    private final BlacklistRepository blacklistRepository;

    public RiskEngineService(RiskRuleRepository riskRuleRepository,
                             RiskEventRepository riskEventRepository,
                             BlacklistRepository blacklistRepository) {
        this.riskRuleRepository = riskRuleRepository;
        this.riskEventRepository = riskEventRepository;
        this.blacklistRepository = blacklistRepository;
    }

    // ── Risk Rules ──────────────────────────────────────────────────────────

    public RiskRule createRule(RiskRuleDTO dto) {
        RiskRule rule = new RiskRule();
        rule.setName(dto.getName());
        rule.setExpression(dto.getExpression());
        rule.setSeverity(dto.getSeverity());
        rule.setStatus(dto.getStatus());
        return riskRuleRepository.save(rule);
    }

    public List<RiskRule> getAllRules() {
        return riskRuleRepository.findAll();
    }

    public RiskRule getRuleById(Long id) {
        return riskRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risk rule not found with id: " + id));
    }

    public RiskRule updateRule(Long id, RiskRuleDTO dto) {
        RiskRule rule = getRuleById(id);
        rule.setName(dto.getName());
        rule.setExpression(dto.getExpression());
        rule.setSeverity(dto.getSeverity());
        rule.setStatus(dto.getStatus());
        return riskRuleRepository.save(rule);
    }

    public void deleteRule(Long id) {
        RiskRule rule = getRuleById(id);
        rule.setStatus("INACTIVE");
        riskRuleRepository.save(rule);
    }

    // ── Risk Evaluation ─────────────────────────────────────────────────────

    public RiskEvent evaluateTransaction(Long txnId, Double amount) {
        List<RiskRule> activeRules = riskRuleRepository.findByStatus("ACTIVE");

        RiskEvent event = new RiskEvent();
        event.setTxnId(txnId);
        event.setEventDate(LocalDateTime.now());

        if (!activeRules.isEmpty()) {
            event.setRuleId(activeRules.get(0).getRiskRuleId());
        }

        if (amount > 25000) {
            event.setResult("Block");
            event.setScore(95.0);
        } else {
            event.setResult("Allow");
            event.setScore(10.0);
        }

        return riskEventRepository.save(event);
    }

    // ── Risk Events ─────────────────────────────────────────────────────────

    public List<RiskEvent> getAllEvents() {
        return riskEventRepository.findAll();
    }

    public List<RiskEvent> getEventsByResult(String result) {
        return riskEventRepository.findByResult(result);
    }

    public List<RiskEvent> getEventsByTxnId(Long txnId) {
        return riskEventRepository.findByTxnId(txnId);
    }

    // ── Blacklist ────────────────────────────────────────────────────────────

    public Blacklist addToBlacklist(BlacklistDTO dto) {
        Blacklist entry = new Blacklist();
        entry.setType(dto.getType());
        entry.setValue(dto.getValue());
        entry.setReason(dto.getReason());
        entry.setStatus(dto.getStatus());
        return blacklistRepository.save(entry);
    }

    public List<Blacklist> getAllBlacklist() {
        return blacklistRepository.findAll();
    }

    public List<Blacklist> getBlacklistByType(String type) {
        return blacklistRepository.findByType(type);
    }

    public Blacklist removeFromBlacklist(Long id) {
        Blacklist entry = blacklistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blacklist entry not found with id: " + id));
        entry.setStatus("INACTIVE");
        return blacklistRepository.save(entry);
    }
}
