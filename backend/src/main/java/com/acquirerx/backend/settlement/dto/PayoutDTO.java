package com.acquirerx.backend.settlement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PayoutDTO {
    private Long payoutId;
    private BigDecimal amount;
    private LocalDate payoutDate;
    private String status;

    public Long getPayoutId() { return payoutId; }
    public void setPayoutId(Long payoutId) { this.payoutId = payoutId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getPayoutDate() { return payoutDate; }
    public void setPayoutDate(LocalDate payoutDate) { this.payoutDate = payoutDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
