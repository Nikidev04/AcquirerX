package com.acquirerx.backend.terminal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "terminal")
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long terminalId;

    private String storeId;

    @Column(unique = true, nullable = false, length = 8)
    private String tid;

    private String brandModel;
    private String capability;

    private Long paramProfileId;

    @Column(name = "tmk_reference")
    private String tmkReference;

    private String status;

    public Terminal() {}

    public Long getTerminalId() { return terminalId; }
    public void setTerminalId(Long terminalId) { this.terminalId = terminalId; }

    public String getStoreId() { return storeId; }
    public void setStoreId(String storeId) { this.storeId = storeId; }

    public String getTid() { return tid; }
    public void setTid(String tid) { this.tid = tid; }

    public String getBrandModel() { return brandModel; }
    public void setBrandModel(String brandModel) { this.brandModel = brandModel; }

    public String getCapability() { return capability; }
    public void setCapability(String capability) { this.capability = capability; }

    public Long getParamProfileId() { return paramProfileId; }
    public void setParamProfileId(Long paramProfileId) { this.paramProfileId = paramProfileId; }

    public String getTmkReference() { return tmkReference; }
    public void setTmkReference(String tmkReference) { this.tmkReference = tmkReference; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
