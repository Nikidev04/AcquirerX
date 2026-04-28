package com.acquirerx.backend.risk.repository;

import com.acquirerx.backend.risk.entity.RiskEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskEventRepository extends JpaRepository<RiskEvent, Long> {

    List<RiskEvent> findByResult(String result);

    List<RiskEvent> findByTxnId(Long txnId);
}
