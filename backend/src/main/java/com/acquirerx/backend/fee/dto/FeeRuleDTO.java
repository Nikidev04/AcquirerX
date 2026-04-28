package com.acquirerx.backend.fee.dto;

import java.time.LocalDate;

public class FeeRuleDTO {

    private String ruleType;
    private String criteriaJson;
    private Double ratePct;
    private Double flatFee;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    // Getters and Setters

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
}
