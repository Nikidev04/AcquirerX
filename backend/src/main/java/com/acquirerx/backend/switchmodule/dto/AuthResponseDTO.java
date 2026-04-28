package com.acquirerx.backend.switchmodule.dto;

public class AuthResponseDTO {

    private String authCode;
    private String responseCode;
    private String status;

    public AuthResponseDTO() {}

    public String getAuthCode() { return authCode; }
    public void setAuthCode(String authCode) { this.authCode = authCode; }

    public String getResponseCode() { return responseCode; }
    public void setResponseCode(String responseCode) { this.responseCode = responseCode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
