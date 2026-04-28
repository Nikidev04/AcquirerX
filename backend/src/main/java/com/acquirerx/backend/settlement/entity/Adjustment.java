package com.acquirerx.backend.settlement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Stores financial corrections like overcharges, refunds, or disputes.
 * These are "Sticky Notes" that stay PENDING until a settlement cycle clears them.
 */
@Entity
public class Adjustment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adjustmentId;

    private Long merchantId;
    private Long txnId;          // Reference to the original problematic transaction
    private BigDecimal amount;   // Negative for debts (e.g., -900.00), Positive for credits
    private String reason;       // e.g., "PARTIAL_REFUND", "CHARGEBACK"
    private String status;       // PENDING (awaiting recovery), SETTLED (deducted from payout)
    private LocalDate postedDate;

    // Getters
    public Long getAdjustmentId() {
        return adjustmentId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public Long getTxnId() {
        return txnId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getPostedDate() {
        return postedDate;
    }

    // Setters
    public void setAdjustmentId(Long adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPostedDate(LocalDate postedDate) {
        this.postedDate = postedDate;
    }
}