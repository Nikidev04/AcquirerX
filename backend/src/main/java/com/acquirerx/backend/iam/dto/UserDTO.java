package com.acquirerx.backend.iam.dto;

/**
 * Simple user view for API responses.
 * 'status' kept as String to avoid coupling to the entity enum.
 */
public record UserDTO(
        Integer userId,
        String  name,
        String  role,
        String  email,
        String  phone,
        String  status
) {}