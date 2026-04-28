package com.acquirerx.backend.reconciliation.dto;

import java.time.LocalDateTime;

public record ExceptionCaseDTO(
        Long id,
        String referenceId,
        String category,
        LocalDateTime createdDate,
        String status
){

}
