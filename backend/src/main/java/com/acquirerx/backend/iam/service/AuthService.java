package com.acquirerx.backend.iam.service;

import com.acquirerx.backend.exception.ResourceNotFoundException;
import com.acquirerx.backend.iam.dto.*;
import com.acquirerx.backend.iam.entity.*;
import com.acquirerx.backend.iam.repository.*;
import com.acquirerx.backend.iam.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuditLogRepository auditRepo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository ur, AuditLogRepository ar, JwtUtil ju) {
        this.userRepository = ur;
        this.auditRepo = ar;
        this.jwtUtil = ju;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {

        User user = userRepository.findByEmail(dto.email()).orElse(null);

        if (user == null) {
            writeAudit(null, "LOGIN_FAILED", "iam", "email_not_found");
            throw new IllegalArgumentException("Invalid credentials");
        }

        if (!encoder.matches(dto.password(), user.getPasswordHash())) {
            writeAudit(user, "LOGIN_FAILED", "iam", "wrong_password");
            throw new IllegalArgumentException("Invalid credentials");
        }

        if (user.getStatus() != User.Status.ACTIVE) {
            writeAudit(user, "LOGIN_FAILED", "iam", "inactive_user");
            throw new IllegalArgumentException("User inactive");
        }

        writeAudit(user, "LOGIN_SUCCESS", "iam", null);

        String token = jwtUtil.createToken(String.valueOf(user.getUserId()), user.getRole());

        return new LoginResponseDTO(token, 3600);
    }

    public String changePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        if (!encoder.matches(oldPassword, user.getPasswordHash())) {
            writeAudit(user, "CHANGE_PASSWORD_FAILED", "iam", "wrong_old_password");
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPasswordHash(encoder.encode(newPassword));
        userRepository.save(user);

        writeAudit(user, "CHANGE_PASSWORD_SUCCESS", "iam", null);

        return "Password changed successfully";
    }

    private void writeAudit(User user, String action, String res, String meta) {
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setResource(res);
        log.setMetadata(meta);
        log.setTimestamp(LocalDateTime.now());
        auditRepo.save(log);
    }
}
