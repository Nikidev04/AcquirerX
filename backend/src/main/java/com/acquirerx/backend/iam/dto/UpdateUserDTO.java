package com.acquirerx.backend.iam.dto;

public record UpdateUserDTO(
        String name,
        String phone,
        String role,
        String status
) {}