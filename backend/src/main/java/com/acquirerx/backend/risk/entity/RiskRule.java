package com.acquirerx.backend.risk.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "risk_rule")
public class RiskRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long riskRuleId;

    private String name;
    private String expression;
    private String severity;
    private String status;

    public Long getRiskRuleId() { return riskRuleId; }
    public void setRiskRuleId(Long riskRuleId) { this.riskRuleId = riskRuleId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
