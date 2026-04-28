package com.acquirerx.backend.terminal.dto;

public class TerminalRequestDTO {
    private String storeId;
    private String brandModel;
    private String capability;
    private Long paramProfileId;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getBrandModel() {
        return brandModel;
    }

    public void setBrandModel(String brandModel) {
        this.brandModel = brandModel;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    public Long getParamProfileId() {
        return paramProfileId;
    }

    public void setParamProfileId(Long paramProfileId) {
        this.paramProfileId = paramProfileId;
    }
}
