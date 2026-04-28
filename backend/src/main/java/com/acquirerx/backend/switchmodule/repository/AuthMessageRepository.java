package com.acquirerx.backend.switchmodule.repository;

import com.acquirerx.backend.switchmodule.entity.AuthMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthMessageRepository extends JpaRepository<AuthMessage, Long> {

    List<AuthMessage> findByTerminalId(Long terminalId);

    List<AuthMessage> findByMerchantId(Long merchantId);

    List<AuthMessage> findByStatus(String status);
}
