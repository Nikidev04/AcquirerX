package com.acquirerx.backend.risk.dto;

public class BlacklistDTO {

    private Long blacklistId;
    private String type;
    private String value;
    private String reason;
    private String status;

    public Long getBlacklistId() { return blacklistId; }
    public void setBlacklistId(Long blacklistId) { this.blacklistId = blacklistId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
