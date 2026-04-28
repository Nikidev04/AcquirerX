package com.acquirerx.backend.dispute.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Dispute Case.
 * This class defines the exact JSON structure that our API expects to receive
 * and what it will send back to the frontend. It hides internal database complexity.
 */
public class DisputeCaseDTO {

    // The ID is not required when creating a NEW case (the database generates it),
    // but it will be present when we return an existing case to the frontend.
    private Integer caseId;

    // -------------------------------------------------------------------------
    // VALIDATION RULES
    // These annotations ensure the frontend sends valid JSON payloads.
    // -------------------------------------------------------------------------

    @NotNull(message = "Transaction ID is required to open a dispute")
    private Integer txnId;

    @NotBlank(message = "Lifecycle stage must be provided (e.g., Retrieval, Chargeback)")
    private String stage;

    // Reason code is optional, so we don't put a @NotNull annotation here
    private String reasonCode;

    // Usually set by the backend when created, but we include it in the DTO
    // so the frontend can read when the case was opened.
    private LocalDateTime openedDate;

    @NotBlank(message = "Case status cannot be blank (e.g., Open, Closed)")
    private String status;

    public DisputeCaseDTO() {
    }

    public DisputeCaseDTO(Integer caseId, Integer txnId, String stage, String reasonCode, LocalDateTime openedDate, String status) {
        this.caseId = caseId;
        this.txnId = txnId;
        this.stage = stage;
        this.reasonCode = reasonCode;
        this.openedDate = openedDate;
        this.status = status;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getTxnId() {
        return txnId;
    }

    public void setTxnId(Integer txnId) {
        this.txnId = txnId;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public LocalDateTime getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(LocalDateTime openedDate) {
        this.openedDate = openedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Note: We are keeping this DTO lightweight. If the frontend requests a specific case,
    // we can easily add List<DisputeDocumentDTO> or List<DisputeActionDTO> here later
    // to send everything in one giant JSON package!
}