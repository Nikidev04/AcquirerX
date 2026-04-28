package com.acquirerx.backend.fee.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fee_rule")
public class FeeRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feeRuleId;

    private String ruleType;
    private String criteriaJson;
    private Double ratePct;
    private Double flatFee;

    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    private String status;

    // Getters and Setters

    public Long getFeeRuleId() { return feeRuleId; }
    public void setFeeRuleId(Long feeRuleId) { this.feeRuleId = feeRuleId; }

    public String getRuleType() { return ruleType; }
    public void setRuleType(String ruleType) { this.ruleType = ruleType; }

    public String getCriteriaJson() { return criteriaJson; }
    public void setCriteriaJson(String criteriaJson) { this.criteriaJson = criteriaJson; }

    public Double getRatePct() { return ratePct; }
    public void setRatePct(Double ratePct) { this.ratePct = ratePct; }

    public Double getFlatFee() { return flatFee; }
    public void setFlatFee(Double flatFee) { this.flatFee = flatFee; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
