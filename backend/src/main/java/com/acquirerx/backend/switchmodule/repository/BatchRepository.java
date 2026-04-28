package com.acquirerx.backend.switchmodule.repository;

import com.acquirerx.backend.switchmodule.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BatchRepository extends JpaRepository<Batch, Long> {

	Optional<Batch> findByTerminalIdAndStatus(Long terminalId, String status);

	List<Batch> findByTerminalId(Long terminalId);

	List<Batch> findByStatus(String status);
}
