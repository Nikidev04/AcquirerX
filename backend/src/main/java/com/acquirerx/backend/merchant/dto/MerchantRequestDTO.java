package com.acquirerx.backend.merchant.dto;

import com.acquirerx.backend.common.enums.RiskLevel;
import com.acquirerx.backend.common.enums.Status;
import com.acquirerx.backend.common.enums.SettlementCycle;
import java.math.BigDecimal;

public class MerchantRequestDTO {
    // Basic Info
    private String legalName;
    private String doingBusinessAs;
    private String mcc;
    private String contactInfo;
    private RiskLevel riskLevel;
    private Status status;

    // Settlement Info
    private SettlementCycle settlementCycle;
    private String bankAccountNumber;
    private String swiftCode;

    // Pricing Info
    private String pricingType; // MDR, IC++, BLENDED
    private BigDecimal percentage;
    private BigDecimal baseFee;

    // Getters
    public String getLegalName() {
        return legalName;
    }

    public String getDoingBusinessAs() {
        return doingBusinessAs;
    }

    public String getMcc() {
        return mcc;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public Status getStatus() {
        return status;
    }

    public SettlementCycle getSettlementCycle() {
        return settlementCycle;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public String getPricingType() {
        return pricingType;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public BigDecimal getBaseFee() {
        return baseFee;
    }

    // Setters
    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public void setDoingBusinessAs(String doingBusinessAs) {
        this.doingBusinessAs = doingBusinessAs;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSettlementCycle(SettlementCycle settlementCycle) {
        this.settlementCycle = settlementCycle;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public void setPricingType(String pricingType) {
        this.pricingType = pricingType;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public void setBaseFee(BigDecimal baseFee) {
        this.baseFee = baseFee;
    }
}