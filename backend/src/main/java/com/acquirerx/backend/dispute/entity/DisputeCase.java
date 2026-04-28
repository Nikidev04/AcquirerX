package com.acquirerx.backend.dispute.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "dispute_case")
public class DisputeCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer caseId; // Changed from caseID to caseId

    @Column(nullable = false)
    private Integer txnId; // Changed from txnID to txnId

    // We will store Enum as String in DB for readability. E.g., Retrieval, Chargeback, Representment, Arbitration
    @Column(nullable = false)
    private String stage;

    private String reasonCode; //

    @Column(nullable = false)
    private LocalDateTime openedDate; //

    // E.g., Open, Closed
    @Column(nullable = false)
    private String status;

    // One Case can have Many Documents
    @OneToMany(mappedBy = "disputeCase", cascade = CascadeType.ALL)
    private List<DisputeDocument> documents;

    // One Case can have Many Actions (Audit Trail)
    @OneToMany(mappedBy = "disputeCase", cascade = CascadeType.ALL)
    private List<DisputeAction> actions;

    public DisputeCase() {
    }

    public DisputeCase(Integer caseId, Integer txnId, String stage, String reasonCode, LocalDateTime openedDate, String status, List<DisputeDocument> documents, List<DisputeAction> actions) {
        this.caseId = caseId;
        this.txnId = txnId;
        this.stage = stage;
        this.reasonCode = reasonCode;
        this.openedDate = openedDate;
        this.status = status;
        this.documents = documents;
        this.actions = actions;
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

    public List<DisputeDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DisputeDocument> documents) {
        this.documents = documents;
    }

    public List<DisputeAction> getActions() {
        return actions;
    }

    public void setActions(List<DisputeAction> actions) {
        this.actions = actions;
    }
}