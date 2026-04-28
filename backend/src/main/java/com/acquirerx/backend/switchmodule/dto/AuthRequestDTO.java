package com.acquirerx.backend.switchmodule.dto;

public class AuthRequestDTO {

    private Long terminalId;
    private Long merchantId;

    private Double amount;
    private String currency;

    private String txnType;
    private String network;

    public AuthRequestDTO() {}

    public Long getTerminalId() { return terminalId; }
    public void setTerminalId(Long terminalId) { this.terminalId = terminalId; }

    public Long getMerchantId() { return merchantId; }
    public void setMerchantId(Long merchantId) { this.merchantId = merchantId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getTxnType() { return txnType; }
    public void setTxnType(String txnType) { this.txnType = txnType; }

    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }
}
