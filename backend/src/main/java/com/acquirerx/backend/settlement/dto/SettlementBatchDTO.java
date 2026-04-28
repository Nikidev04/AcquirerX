package com.acquirerx.backend.settlement.dto;

import java.math.BigDecimal;

public class SettlementBatchDTO {
    private Long settleBatchId;
    private BigDecimal grossAmount;
    private BigDecimal netAmount;
    private String status;

    public Long getSettleBatchId() { return settleBatchId; }
    public void setSettleBatchId(Long settleBatchId) { this.settleBatchId = settleBatchId; }

    public BigDecimal getGrossAmount() { return grossAmount; }
    public void setGrossAmount(BigDecimal grossAmount) { this.grossAmount = grossAmount; }

    public BigDecimal getNetAmount() { return netAmount; }
    public void setNetAmount(BigDecimal netAmount) { this.netAmount = netAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
