package com.acquirerx.backend.fee.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "txn")
public class Txn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long txnId;

    private Long authId;
    private Long merchantId;
    private Long storeId;
    private Long terminalId;

    private Double amount;
    private String currency;

    private Double schemeFee;
    private Double interchangeFee;
    private Double acquirerMarkup;
    private Double netMerchantAmount;

    private LocalDateTime txnDate;
    private String status;

    // Getters and Setters

    public Long getTxnId() { return txnId; }
    public void setTxnId(Long txnId) { this.txnId = txnId; }

    public Long getAuthId() { return authId; }
    public void setAuthId(Long authId) { this.authId = authId; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public Long getStoreId() { return storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }

    public Long getTerminalId() { return terminalId; }
    public void setTerminalId(Long terminalId) { this.terminalId = terminalId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public Double getSchemeFee() { return schemeFee; }
    public void setSchemeFee(Double schemeFee) { this.schemeFee = schemeFee; }

    public Double getInterchangeFee() { return interchangeFee; }
    public void setInterchangeFee(Double interchangeFee) { this.interchangeFee = interchangeFee; }

    public Double getAcquirerMarkup() { return acquirerMarkup; }
    public void setAcquirerMarkup(Double acquirerMarkup) { this.acquirerMarkup = acquirerMarkup; }

    public Double getNetMerchantAmount() { return netMerchantAmount; }
    public void setNetMerchantAmount(Double netMerchantAmount) { this.netMerchantAmount = netMerchantAmount; }

    public LocalDateTime getTxnDate() { return txnDate; }
    public void setTxnDate(LocalDateTime txnDate) { this.txnDate = txnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}