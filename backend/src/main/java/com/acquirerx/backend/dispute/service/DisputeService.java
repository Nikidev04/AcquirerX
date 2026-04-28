package com.acquirerx.backend.dispute.service;

import com.acquirerx.backend.dispute.dto.DisputeActionDTO;
import com.acquirerx.backend.dispute.dto.DisputeCaseDTO;
import com.acquirerx.backend.dispute.dto.DisputeDocumentDTO;
import com.acquirerx.backend.dispute.entity.DisputeAction;
import com.acquirerx.backend.dispute.entity.DisputeCase;
import com.acquirerx.backend.dispute.entity.DisputeDocument;
import com.acquirerx.backend.dispute.repository.DisputeActionRepository;
import com.acquirerx.backend.dispute.repository.DisputeCaseRepository;
import com.acquirerx.backend.dispute.repository.DisputeDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service class for handling all business logic related to Disputes.
 * Acts as the "Traffic Cop" enforcing the rules of the dispute lifecycle.
 */
@Service // Tells Spring Boot this is our business logic layer
public class DisputeService {

    // -------------------------------------------------------------------------
    // DEPENDENCIES (The Pantry)
    // -------------------------------------------------------------------------
    private final DisputeCaseRepository caseRepo;
    private final DisputeActionRepository actionRepo;
    private final DisputeDocumentRepository docRepo;

    public DisputeService(
            DisputeCaseRepository caseRepo,
            DisputeActionRepository actionRepo,
            DisputeDocumentRepository docRepo
    ) {
        this.caseRepo = caseRepo;
        this.actionRepo = actionRepo;
        this.docRepo = docRepo;
    }

    // -------------------------------------------------------------------------
    // 1. CREATE DISPUTE
    // -------------------------------------------------------------------------
    @Transactional // Ensures if anything fails, the whole database save is cancelled
    public DisputeCaseDTO createDisputeCase(DisputeCaseDTO dto, Integer actorId) {
        // Step 1: Create the new database entity
        DisputeCase newCase = new DisputeCase();
        newCase.setTxnId(dto.getTxnId());
        newCase.setStage(dto.getStage()); // e.g., "Retrieval" or "Chargeback"
        newCase.setReasonCode(dto.getReasonCode());
        newCase.setOpenedDate(LocalDateTime.now());
        newCase.setStatus("Open");

        // Step 2: Save it to the database
        DisputeCase savedCase = caseRepo.save(newCase);

        // Step 3: Secretly log the action in the audit trail
        logAction(savedCase, "Case Created", actorId, "Initial dispute opened for Txn: " + dto.getTxnId());

        // Step 4: Package it back into a DTO to send to the frontend
        dto.setCaseId(savedCase.getCaseId());
        dto.setOpenedDate(savedCase.getOpenedDate());
        dto.setStatus(savedCase.getStatus());
        return dto;
    }

    // -------------------------------------------------------------------------
    // 2. FETCH DISPUTES
    // -------------------------------------------------------------------------
    public List<DisputeCaseDTO> getAllDisputes() {
        // Fetches all cases and converts them from Entities to DTOs
        return caseRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DisputeCaseDTO getDisputeById(Integer caseId) {
        Integer safeCaseId = Objects.requireNonNull(caseId, "caseId is required");
        // Tries to find the case. If it doesn't exist, throws an error.
        DisputeCase dispute = caseRepo.findById(safeCaseId)
            .orElseThrow(() -> new RuntimeException("Dispute Case not found with ID: " + safeCaseId));
        return convertToDTO(dispute);
    }

    // -------------------------------------------------------------------------
    // 3. ADVANCE STAGE (The Traffic Cop)
    // -------------------------------------------------------------------------
    @Transactional
    public DisputeCaseDTO advanceDisputeStage(Integer caseId, String newStage, Integer actorId, String notes) {
        Integer safeCaseId = Objects.requireNonNull(caseId, "caseId is required");
        DisputeCase dispute = caseRepo.findById(safeCaseId)
                .orElseThrow(() -> new RuntimeException("Dispute Case not found"));

        String oldStage = dispute.getStage();

        // Enforce the Business Rules (Retrieval -> Chargeback -> Representment -> Arbitration)
        // Note: For simplicity, we are just updating the stage here, but in a real enterprise app,
        // you would write strict 'if' statements to prevent illegal stage jumps.
        dispute.setStage(newStage);
        DisputeCase updatedCase = caseRepo.save(dispute);

        // Log the stage change
        logAction(updatedCase, "Stage Changed", actorId, "Moved from " + oldStage + " to " + newStage + ". Notes: " + notes);

        return convertToDTO(updatedCase);
    }

    // -------------------------------------------------------------------------
    // 4. ADD DOCUMENT
    // -------------------------------------------------------------------------
    @Transactional
    public DisputeDocumentDTO addDocumentToDispute(DisputeDocumentDTO dto, Integer actorId) {
        Integer safeCaseId = Objects.requireNonNull(dto.getCaseId(), "caseId is required");
        DisputeCase dispute = caseRepo.findById(safeCaseId)
                .orElseThrow(() -> new RuntimeException("Dispute Case not found"));

        DisputeDocument document = new DisputeDocument();
        document.setDisputeCase(dispute);
        document.setDocType(dto.getDocType());
        document.setUri(dto.getUri());
        document.setUploadedDate(LocalDateTime.now());

        DisputeDocument savedDoc = docRepo.save(document);

        // Log the document upload
        logAction(dispute, "Document Uploaded", actorId, "Uploaded " + dto.getDocType() + " evidence.");

        dto.setDocId(savedDoc.getDocId());
        dto.setUploadedDate(savedDoc.getUploadedDate());
        return dto;
    }

    // -------------------------------------------------------------------------
    // 5. CLOSE DISPUTE
    // -------------------------------------------------------------------------
    @Transactional
    public DisputeCaseDTO closeDispute(Integer caseId, Integer actorId, String notes) {
        DisputeCase dispute = caseRepo.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Dispute Case not found with ID: " + caseId));

        dispute.setStatus("Closed");
        DisputeCase savedCase = caseRepo.save(dispute);

        logAction(savedCase, "Case Closed", actorId, notes);

        return convertToDTO(savedCase);
    }

    // -------------------------------------------------------------------------
    // 6. GET DOCUMENTS BY CASE
    // -------------------------------------------------------------------------
    public List<DisputeDocument> getDocumentsByCase(Integer caseId) {
        caseRepo.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Dispute Case not found"));
        return docRepo.findByDisputeCase_CaseId(caseId);
    }

    // -------------------------------------------------------------------------
    // 7. GET ACTIONS BY CASE
    // -------------------------------------------------------------------------
    public List<DisputeActionDTO> getActionsByCase(Integer caseId) {
        caseRepo.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Dispute Case not found"));
        return actionRepo.findByDisputeCase_CaseId(caseId)
                .stream()
                .map(this::convertActionToDTO)
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------------------------
    // 8. HELPER METHODS
    // -------------------------------------------------------------------------

    /**
     * The "Secret Security Camera" that writes to the audit trail.
     * It is marked 'private' because only the Head Chef (this service) is allowed to use it.
     */
    private void logAction(DisputeCase disputeCase, String actionType, Integer actorId, String notes) {
        DisputeAction action = new DisputeAction();
        action.setDisputeCase(disputeCase);
        action.setActionType(actionType);
        action.setActorId(actorId);
        action.setActionDate(LocalDateTime.now());
        action.setNotes(notes);
        actionRepo.save(action);
    }

    /**
     * Converts a DisputeCase database Entity into a secure DTO for the frontend.
     */
    private DisputeCaseDTO convertToDTO(DisputeCase entity) {
        return new DisputeCaseDTO(
                entity.getCaseId(),
                entity.getTxnId(),
                entity.getStage(),
                entity.getReasonCode(),
                entity.getOpenedDate(),
                entity.getStatus()
        );
    }

    /**
     * Converts a DisputeAction database Entity into a DTO for the frontend.
     */
    private DisputeActionDTO convertActionToDTO(DisputeAction entity) {
        return new DisputeActionDTO(
                entity.getActionId(),
                entity.getDisputeCase().getCaseId(),
                entity.getActionType(),
                entity.getActorId(),
                entity.getActionDate(),
                entity.getNotes()
        );
    }
}