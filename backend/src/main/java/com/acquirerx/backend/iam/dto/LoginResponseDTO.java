package com.acquirerx.backend.iam.dto;

public record LoginResponseDTO(
        String accessToken,
        long   expiresIn
) {}