package com.acquirerx.backend.reporting.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "acquirer_report")
public class AcquirerReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    // Merchant / Network / Period
    @Column(nullable = false)
    private String scope;

    // Nullable – only populated for Merchant-scoped reports
    private Long merchantId;

    // JSON string e.g. {"volume":100,"value":50000,"fees":750,"net":49250,"chargebackRate":0.5}
    @Column(columnDefinition = "TEXT")
    private String metrics;

    @Column(nullable = false)
    private LocalDateTime generatedDate;

    // GENERATED / ARCHIVED
    @Column(nullable = false)
    private String status;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    public AcquirerReport() {
    }

    public AcquirerReport(Long reportId, String scope, Long merchantId,
                          String metrics, LocalDateTime generatedDate, String status) {
        this.reportId = reportId;
        this.scope = scope;
        this.merchantId = merchantId;
        this.metrics = metrics;
        this.generatedDate = generatedDate;
        this.status = status;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

    public LocalDateTime getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDateTime generatedDate) {
        this.generatedDate = generatedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
