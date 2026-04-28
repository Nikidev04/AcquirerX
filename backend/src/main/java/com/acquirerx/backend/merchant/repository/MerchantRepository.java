package com.acquirerx.backend.merchant.repository;

import com.acquirerx.backend.merchant.entity.Merchant;
import com.acquirerx.backend.common.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    // Find merchants by legal name
    List<Merchant> findByLegalNameContaining(String name);

    // Find merchants by status
    List<Merchant> findByStatus(Status status);
    List<Merchant> findByLegalNameContainingIgnoreCase(String name);
}