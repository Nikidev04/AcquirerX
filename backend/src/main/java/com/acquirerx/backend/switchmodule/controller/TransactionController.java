package com.acquirerx.backend.switchmodule.controller;

import com.acquirerx.backend.switchmodule.dto.AuthRequestDTO;
import com.acquirerx.backend.switchmodule.dto.AuthResponseDTO;
import com.acquirerx.backend.switchmodule.entity.AuthMessage;
import com.acquirerx.backend.switchmodule.entity.Batch;
import com.acquirerx.backend.switchmodule.service.AuthorizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	private final AuthorizationService authorizationService;

	public TransactionController(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	@PostMapping("/authorize")
	public AuthResponseDTO authorize(@RequestBody AuthRequestDTO request) {
		return authorizationService.authorize(request);
	}

	@PostMapping("/{authId}/void")
	public ResponseEntity<String> voidTransaction(@PathVariable("authId") Long authId) {
		authorizationService.voidTransaction(authId);
		return ResponseEntity.ok("Transaction voided");
	}

	@PostMapping("/{authId}/refund")
	public ResponseEntity<String> refundTransaction(@PathVariable("authId") Long authId) {
		authorizationService.refundTransaction(authId);
		return ResponseEntity.ok("Refund processed");
	}

	@GetMapping("/{authId}")
	public ResponseEntity<AuthMessage> getTransactionById(@PathVariable("authId") Long authId) {
		return ResponseEntity.ok(authorizationService.getTransactionById(authId));
	}

	@GetMapping("/terminal/{terminalId}")
	public ResponseEntity<List<AuthMessage>> getTransactionsByTerminal(@PathVariable("terminalId") Long terminalId) {
		return ResponseEntity.ok(authorizationService.getTransactionsByTerminal(terminalId));
	}

	@GetMapping("/merchant/{merchantId}")
	public ResponseEntity<List<AuthMessage>> getTransactionsByMerchant(@PathVariable("merchantId") Long merchantId) {
		return ResponseEntity.ok(authorizationService.getTransactionsByMerchant(merchantId));
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<AuthMessage>> getTransactionsByStatus(@PathVariable("status") String status) {
		return ResponseEntity.ok(authorizationService.getTransactionsByStatus(status));
	}

	@PostMapping("/batch/open")
	public ResponseEntity<Batch> openBatch(@RequestParam("terminalId") Long terminalId) {
		return ResponseEntity.ok(authorizationService.openBatch(terminalId));
	}

	@PostMapping("/batch/{batchId}/close")
	public ResponseEntity<Batch> closeBatch(@PathVariable("batchId") Long batchId) {
		return ResponseEntity.ok(authorizationService.closeBatch(batchId));
	}

	@GetMapping("/batch/{batchId}")
	public ResponseEntity<Batch> getBatchById(@PathVariable("batchId") Long batchId) {
		return ResponseEntity.ok(authorizationService.getBatchById(batchId));
	}

	@GetMapping("/batch/terminal/{terminalId}")
	public ResponseEntity<List<Batch>> getBatchesByTerminal(@PathVariable("terminalId") Long terminalId) {
		return ResponseEntity.ok(authorizationService.getBatchesByTerminal(terminalId));
	}
}
