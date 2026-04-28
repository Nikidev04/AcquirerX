package com.acquirerx.backend.reconciliation.service;

import com.acquirerx.backend.exception.ResourceNotFoundException;
import com.acquirerx.backend.reconciliation.dto.ReconFileDTO;
import org.springframework.stereotype.Service;
import com.acquirerx.backend.reconciliation.entity.*;
import com.acquirerx.backend.reconciliation.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReconciliationService {

    private ReconFileRepository reconFileRepository;
    private ReconItemRepository reconItemRepository;
    private ExceptionCaseRepository exceptionCaseRepository;

    public ReconciliationService(
            ReconFileRepository reconFileRepository,
            ReconItemRepository reconItemRepository,
            ExceptionCaseRepository exceptionCaseRepository
    ) {
        this.reconFileRepository = reconFileRepository;
        this.reconItemRepository = reconItemRepository;
        this.exceptionCaseRepository = exceptionCaseRepository;
    }

    // Save uploaded file metadata
    public ReconFile saveReconFile(ReconFile reconFile) {
        return reconFileRepository.save(reconFile);
    }

    // Save a batch of reconciliation items
    public List<ReconItem> saveReconItems(List<ReconItem> items) {
        return reconItemRepository.saveAll(items);
    }

    // Create exception case
    public ExceptionCase createExceptionCase(String referenceId, String categoryStr) {
        ExceptionCase.Category category = java.util.Arrays.stream(ExceptionCase.Category.values())
                .filter(c -> c.name().equalsIgnoreCase(categoryStr))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid category: \"" + categoryStr + "\". Accepted values: MissingTxn, Duplicate, AmountMismatch"));

        ExceptionCase ex = new ExceptionCase();
        ex.setReferenceId(referenceId);
        ex.setCreatedDate(LocalDateTime.now());
        ex.setCategory(category);
        ex.setStatus(ExceptionCase.Status.OPEN);

        return exceptionCaseRepository.save(ex);
    }

    // Fetch all exceptions
    public List<ExceptionCase> getAllExceptions() {
        return exceptionCaseRepository.findAll();
    }

    // --- New ReconFile methods ---

    public ReconFile createReconFile(ReconFileDTO dto) {
        ReconFile reconFile = new ReconFile();
        reconFile.setSource(dto.source());
        reconFile.setFileDate(dto.fileDate() != null ? dto.fileDate() : LocalDate.now());
        reconFile.setRowCount(dto.rowCount());
        reconFile.setStatus(ReconFile.Status.LOADED);
        return reconFileRepository.save(reconFile);
    }

    public List<ReconFile> getAllReconFiles() {
        return reconFileRepository.findAll();
    }

    public ReconFile getReconFileById(Long id) {
        return reconFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReconFile not found: " + id));
    }

    // --- ReconItem methods ---

    public List<ReconItem> getReconItemsByFile(Long reconFileId) {
        return reconItemRepository.findByReconFile_ReconFileId(reconFileId);
    }

    public List<ReconItem> getReconItemsByMatchStatus(String matchStatus) {
        ReconItem.MatchStatus status = java.util.Arrays.stream(ReconItem.MatchStatus.values())
                .filter(s -> s.name().equalsIgnoreCase(matchStatus))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid matchStatus: \"" + matchStatus + "\". Accepted values: MATCHED, UNMATCHED, MISMATCHED"));
        return reconItemRepository.findByMatchStatus(status);
    }

    // --- ExceptionCase update methods ---

    public ExceptionCase getExceptionById(Long id) {
        return exceptionCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ExceptionCase not found: " + id));
    }

    public ExceptionCase resolveException(Long id) {
        ExceptionCase ex = getExceptionById(id);
        ex.setStatus(ExceptionCase.Status.RESOLVED);
        return exceptionCaseRepository.save(ex);
    }

    public ExceptionCase writeOffException(Long id) {
        ExceptionCase ex = getExceptionById(id);
        ex.setStatus(ExceptionCase.Status.WRITTEN_OFF);
        return exceptionCaseRepository.save(ex);
    }
}