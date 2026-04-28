package com.acquirerx.backend.reconciliation.dto;

import java.time.LocalDate;

public record ReconFileDTO(
        Long id,
        String source,
        LocalDate fileDate,
        Integer rowCount,
        String status
){

}

