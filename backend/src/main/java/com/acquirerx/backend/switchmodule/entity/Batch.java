package com.acquirerx.backend.switchmodule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "batch")
public class Batch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long batchId;

	private Long terminalId;

	private LocalDateTime openTime;
	private LocalDateTime closeTime;

	private String status;

	public Batch() {}

	public Long getBatchId() { return batchId; }
	public void setBatchId(Long batchId) { this.batchId = batchId; }

	public Long getTerminalId() { return terminalId; }
	public void setTerminalId(Long terminalId) { this.terminalId = terminalId; }

	public LocalDateTime getOpenTime() { return openTime; }
	public void setOpenTime(LocalDateTime openTime) { this.openTime = openTime; }

	public LocalDateTime getCloseTime() { return closeTime; }
	public void setCloseTime(LocalDateTime closeTime) { this.closeTime = closeTime; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
}
