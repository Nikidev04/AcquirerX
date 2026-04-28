package com.acquirerx.backend.reconciliation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.acquirerx.backend.reconciliation.dto.ExceptionCaseDTO;
import com.acquirerx.backend.reconciliation.dto.ReconFileDTO;
import com.acquirerx.backend.reconciliation.entity.ExceptionCase;
import com.acquirerx.backend.reconciliation.entity.ReconFile;
import com.acquirerx.backend.reconciliation.entity.ReconItem;
import com.acquirerx.backend.reconciliation.service.ReconciliationService;

import java.util.List;

@RestController
@RequestMapping("/api/recon")
public class ReconciliationController {

    private ReconciliationService reconciliationService;

    public ReconciliationController(ReconciliationService reconciliationService) {
        this.reconciliationService = reconciliationService;
    }

    // Create exception case
    @PostMapping("/exception")
    public ExceptionCaseDTO createException(@RequestParam("referenceId") String referenceId,
                                            @RequestParam("category") String category) {

        ExceptionCase ex = reconciliationService.createExceptionCase(referenceId, category);

        return new ExceptionCaseDTO(
                ex.getExceptionId(),
                ex.getReferenceId(),
                ex.getCategory().name(),
                ex.getCreatedDate(),
                ex.getStatus().name()
        );
    }

    // Get all exceptions
    @GetMapping("/exceptions")
    public List<ExceptionCase> getExceptions() {
        return reconciliationService.getAllExceptions();
    }

    // --- ReconFile endpoints ---

    @PostMapping("/files")
    public ResponseEntity<ReconFile> createReconFile(@RequestBody ReconFileDTO dto) {
        return ResponseEntity.ok(reconciliationService.createReconFile(dto));
    }

    @GetMapping("/files")
    public ResponseEntity<List<ReconFile>> getAllReconFiles() {
        return ResponseEntity.ok(reconciliationService.getAllReconFiles());
    }

    @GetMapping("/files/{fileId}")
    public ResponseEntity<ReconFile> getReconFileById(@PathVariable("fileId") Long fileId) {
        return ResponseEntity.ok(reconciliationService.getReconFileById(fileId));
    }

    @GetMapping("/files/{fileId}/items")
    public ResponseEntity<List<ReconItem>> getReconItemsByFile(@PathVariable("fileId") Long fileId) {
        return ResponseEntity.ok(reconciliationService.getReconItemsByFile(fileId));
    }

    // --- ReconItem endpoints ---

    @GetMapping("/items/status/{matchStatus}")
    public ResponseEntity<List<ReconItem>> getReconItemsByMatchStatus(@PathVariable("matchStatus") String matchStatus) {
        return ResponseEntity.ok(reconciliationService.getReconItemsByMatchStatus(matchStatus));
    }

    // --- ExceptionCase update endpoints ---

    @GetMapping("/exceptions/{exceptionId}")
    public ResponseEntity<ExceptionCase> getExceptionById(@PathVariable("exceptionId") Long exceptionId) {
        return ResponseEntity.ok(reconciliationService.getExceptionById(exceptionId));
    }

    @PutMapping("/exceptions/{exceptionId}/resolve")
    public ResponseEntity<ExceptionCase> resolveException(@PathVariable("exceptionId") Long exceptionId) {
        return ResponseEntity.ok(reconciliationService.resolveException(exceptionId));
    }

    @PutMapping("/exceptions/{exceptionId}/writeoff")
    public ResponseEntity<ExceptionCase> writeOffException(@PathVariable("exceptionId") Long exceptionId) {
        return ResponseEntity.ok(reconciliationService.writeOffException(exceptionId));
    }
}