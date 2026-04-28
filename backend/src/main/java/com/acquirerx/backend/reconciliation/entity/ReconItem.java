package com.acquirerx.backend.reconciliation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recon_item")
public class ReconItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reconItemId;

    @ManyToOne
    @JoinColumn(name = "recon_file_id")
    private ReconFile reconFile;

    private String reference;     // TxnID / AuthID / NetworkRef
    private Double amount;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    private String notes;

    public enum MatchStatus {
        MATCHED, UNMATCHED, MISMATCHED
    }

    public Long getReconItemId() {
        return reconItemId;
    }

    public void setReconItemId(Long reconItemId) {
        this.reconItemId = reconItemId;
    }

    public ReconFile getReconFile() {
        return reconFile;
    }

    public void setReconFile(ReconFile reconFile) {
        this.reconFile = reconFile;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}