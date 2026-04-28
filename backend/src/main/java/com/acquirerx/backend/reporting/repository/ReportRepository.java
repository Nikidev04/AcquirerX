package com.acquirerx.backend.reporting.repository;

import com.acquirerx.backend.reporting.entity.AcquirerReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<AcquirerReport, Long> {

    List<AcquirerReport> findByScope(String scope);

    List<AcquirerReport> findByMerchantId(Long merchantId);
}
