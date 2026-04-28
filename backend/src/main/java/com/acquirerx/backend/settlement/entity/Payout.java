package com.acquirerx.backend.settlement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The actual instruction to move money to the merchant's bank account.
 * This is triggered only if SettlementBatch.netAmount > 0.
 */
@Entity
public class Payout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payoutId;

    private Long settleBatchId; // Foreign key to the calculation summary
    private BigDecimal amount;
    private String bankAccountRef; // Merchant's masked bank account/IFSC
    private LocalDate payoutDate;
    private String status;       // INITIATED, SUCCESS, FAILED

    // Getters
    public Long getPayoutId() {
        return payoutId;
    }

    public Long getSettleBatchId() {
        return settleBatchId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getBankAccountRef() {
        return bankAccountRef;
    }

    public LocalDate getPayoutDate() {
        return payoutDate;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setPayoutId(Long payoutId) {
        this.payoutId = payoutId;
    }

    public void setSettleBatchId(Long settleBatchId) {
        this.settleBatchId = settleBatchId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setBankAccountRef(String bankAccountRef) {
        this.bankAccountRef = bankAccountRef;
    }

    public void setPayoutDate(LocalDate payoutDate) {
        this.payoutDate = payoutDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}