package com.acquirerx.backend.terminal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "param_profile")
public class ParamProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paramProfileId;

    private String name;

    @Column(columnDefinition = "JSON")
    private String paramsJson; // e.g., {"contactless": true, "max_amt": 5000}

    private String version;
    private String status; // ACTIVE, INACTIVE

    public Long getParamProfileId() {
        return paramProfileId;
    }

    public void setParamProfileId(Long paramProfileId) {
        this.paramProfileId = paramProfileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParamsJson() {
        return paramsJson;
    }

    public void setParamsJson(String paramsJson) {
        this.paramsJson = paramsJson;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}