package com.acquirerx.backend.risk.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_event")
public class RiskEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long riskEventId;

    private Long txnId;
    private Long ruleId;
    private Double score;
    private String result;
    private LocalDateTime eventDate;

    public Long getRiskEventId() { return riskEventId; }
    public void setRiskEventId(Long riskEventId) { this.riskEventId = riskEventId; }

    public Long getTxnId() { return txnId; }
    public void setTxnId(Long txnId) { this.txnId = txnId; }

    public Long getRuleId() { return ruleId; }
    public void setRuleId(Long ruleId) { this.ruleId = ruleId; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public LocalDateTime getEventDate() { return eventDate; }
    public void setEventDate(LocalDateTime eventDate) { this.eventDate = eventDate; }
}
