package com.acquirerx.backend.reconciliation.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "recon_file")
public class ReconFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reconFileId;
    private String source;
    private LocalDate fileDate;
    private Integer rowCount;
    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status{
        LOADED,FAILED,PROCESSED
    }

    public Long getReconFileId() {
        return reconFileId;
    }

    public void setReconFileId(Long reconFileId) {
        this.reconFileId = reconFileId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDate getFileDate() {
        return fileDate;
    }

    public void setFileDate(LocalDate fileDate) {
        this.fileDate = fileDate;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
