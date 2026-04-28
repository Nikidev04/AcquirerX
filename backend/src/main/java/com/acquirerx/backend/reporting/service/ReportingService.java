package com.acquirerx.backend.reporting.service;

import com.acquirerx.backend.exception.ResourceNotFoundException;
import com.acquirerx.backend.reporting.dto.ReportDTO;
import com.acquirerx.backend.reporting.entity.AcquirerReport;
import com.acquirerx.backend.reporting.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportingService {

    private final ReportRepository reportRepository;

    public ReportingService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // -------------------------------------------------------------------------
    // Generate a new report
    // -------------------------------------------------------------------------

    public AcquirerReport generateReport(ReportDTO dto) {
        AcquirerReport report = new AcquirerReport();
        report.setScope(dto.getScope());
        report.setMerchantId(dto.getMerchantId());
        report.setMetrics(dto.getMetrics());
        report.setGeneratedDate(LocalDateTime.now());
        report.setStatus("GENERATED");
        return reportRepository.save(report);
    }

    // -------------------------------------------------------------------------
    // Retrieve reports
    // -------------------------------------------------------------------------

    public List<AcquirerReport> getAllReports() {
        return reportRepository.findAll();
    }

    public AcquirerReport getReportById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found: " + id));
    }

    public List<AcquirerReport> getReportsByScope(String scope) {
        return reportRepository.findByScope(scope);
    }

    public List<AcquirerReport> getReportsByMerchant(Long merchantId) {
        return reportRepository.findByMerchantId(merchantId);
    }

    // -------------------------------------------------------------------------
    // Archive (soft-delete) a report
    // -------------------------------------------------------------------------

    public AcquirerReport deleteReport(Long id) {
        AcquirerReport report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found: " + id));
        report.setStatus("ARCHIVED");
        return reportRepository.save(report);
    }
}
