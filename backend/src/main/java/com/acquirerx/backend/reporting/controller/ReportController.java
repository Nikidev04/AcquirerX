package com.acquirerx.backend.reporting.controller;

import com.acquirerx.backend.reporting.dto.ReportDTO;
import com.acquirerx.backend.reporting.entity.AcquirerReport;
import com.acquirerx.backend.reporting.service.ReportingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportingService reportingService;

    public ReportController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    // -------------------------------------------------------------------------
    // POST /api/reports/generate  – Generate a new report
    // -------------------------------------------------------------------------

    @PostMapping("/generate")
    public ResponseEntity<AcquirerReport> generateReport(@RequestBody ReportDTO dto) {
        AcquirerReport report = reportingService.generateReport(dto);
        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }

    // -------------------------------------------------------------------------
    // GET /api/reports  – Fetch all reports
    // -------------------------------------------------------------------------

    @GetMapping
    public ResponseEntity<List<AcquirerReport>> getAllReports() {
        List<AcquirerReport> reports = reportingService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    // -------------------------------------------------------------------------
    // GET /api/reports/{reportId}  – Fetch one report by ID
    // -------------------------------------------------------------------------

    @GetMapping("/{reportId}")
    public ResponseEntity<AcquirerReport> getReportById(@PathVariable("reportId") Long reportId) {
        AcquirerReport report = reportingService.getReportById(reportId);
        return ResponseEntity.ok(report);
    }

    // -------------------------------------------------------------------------
    // GET /api/reports/scope/{scope}  – Fetch reports by scope
    // -------------------------------------------------------------------------

    @GetMapping("/scope/{scope}")
    public ResponseEntity<List<AcquirerReport>> getReportsByScope(@PathVariable("scope") String scope) {
        List<AcquirerReport> reports = reportingService.getReportsByScope(scope);
        return ResponseEntity.ok(reports);
    }

    // -------------------------------------------------------------------------
    // GET /api/reports/merchant/{merchantId}  – Fetch reports by merchant
    // -------------------------------------------------------------------------

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<List<AcquirerReport>> getReportsByMerchant(@PathVariable("merchantId") Long merchantId) {
        List<AcquirerReport> reports = reportingService.getReportsByMerchant(merchantId);
        return ResponseEntity.ok(reports);
    }

    // -------------------------------------------------------------------------
    // DELETE /api/reports/{reportId}  – Archive (soft-delete) a report
    // -------------------------------------------------------------------------

    @DeleteMapping("/{reportId}")
    public ResponseEntity<AcquirerReport> deleteReport(@PathVariable("reportId") Long reportId) {
        AcquirerReport archived = reportingService.deleteReport(reportId);
        return ResponseEntity.ok(archived);
    }
}
