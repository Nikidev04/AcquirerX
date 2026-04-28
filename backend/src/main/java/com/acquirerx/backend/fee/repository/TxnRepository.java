package com.acquirerx.backend.fee.repository;

import com.acquirerx.backend.fee.entity.Txn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TxnRepository extends JpaRepository<Txn, Long> {

    List<Txn> findByMerchantId(Long merchantId);

    List<Txn> findByStatus(String status);
}
