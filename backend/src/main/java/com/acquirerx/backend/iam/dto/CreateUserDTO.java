package com.acquirerx.backend.iam.dto;

public record CreateUserDTO(
        String email,
        String name,
        String password,
        String phone,
        String role,
        String status
) {}