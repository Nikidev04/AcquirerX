package com.acquirerx.backend.switchmodule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "auth_message")
public class AuthMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;

    private Long terminalId;
    private Long merchantId;

    private String panMasked;
    private String txnType;

    private Double amount;
    private String currency;

    private String authCode;
    private String responseCode;

    private String network;
    private LocalDateTime txnTime;
    private String status;

    public AuthMessage() {}

    public Long getAuthId() { return authId; }
    public void setAuthId(Long authId) { this.authId = authId; }

    public Long getTerminalId() { return terminalId; }
    public void setTerminalId(Long terminalId) { this.terminalId = terminalId; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public String getPanMasked() { return panMasked; }
    public void setPanMasked(String panMasked) { this.panMasked = panMasked; }

    public String getTxnType() { return txnType; }
    public void setTxnType(String txnType) { this.txnType = txnType; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getAuthCode() { return authCode; }
    public void setAuthCode(String authCode) { this.authCode = authCode; }

    public String getResponseCode() { return responseCode; }
    public void setResponseCode(String responseCode) { this.responseCode = responseCode; }

    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }

    public LocalDateTime getTxnTime() { return txnTime; }
    public void setTxnTime(LocalDateTime txnTime) { this.txnTime = txnTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
