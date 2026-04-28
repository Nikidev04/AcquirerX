package com.acquirerx.backend.terminal.dto;

public class TerminalResponseDTO {
    // We send this back to the frontend so they can see the generated TID
    private Long terminalId;
    private String storeId;
    private String tid;
    private String brandModel;
    private String status;
    // Add this so the UI can see the generated key
    private String tmkReference;

    public Long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Long terminalId) {
        this.terminalId = terminalId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getBrandModel() {
        return brandModel;
    }

    public void setBrandModel(String brandModel) {
        this.brandModel = brandModel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTmkReference(String tmkReference) {
        this.tmkReference = tmkReference;
    }

    public String getTmkReference() {
        return tmkReference;
    }
}