package com.acquirerx.backend.settlement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents the final daily accounting summary for a merchant.
 * It calculates the "Take-home" pay after fees and adjustments.
 */
@Entity
public class SettlementBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settleBatchId; // Wrapper class to allow null before DB save

    private Long merchantId;
    private BigDecimal grossAmount;      // Total sales for the day
    private BigDecimal totalFees;        // MDR and Bank charges
    private BigDecimal adjustmentAmount; // Sum of recovered debts/refunds
    private BigDecimal netAmount;        // Final amount to be paid (Gross - Fees + Adjustments)
    private LocalDate postedDate;
    private String status;               // READY, PAID, or DEBT_CARRIED_FORWARD

    // Getters
    public Long getSettleBatchId() {
        return settleBatchId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public BigDecimal getTotalFees() {
        return totalFees;
    }

    public BigDecimal getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public LocalDate getPostedDate() {
        return postedDate;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setSettleBatchId(Long settleBatchId) {
        this.settleBatchId = settleBatchId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public void setTotalFees(BigDecimal totalFees) {
        this.totalFees = totalFees;
    }

    public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public void setPostedDate(LocalDate postedDate) {
        this.postedDate = postedDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}