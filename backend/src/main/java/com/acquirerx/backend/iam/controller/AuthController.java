package com.acquirerx.backend.iam.controller;

import com.acquirerx.backend.iam.dto.*;
import com.acquirerx.backend.iam.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iam/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService as) {
        this.authService = as;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {
        return authService.login(dto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam("userId") Integer userId,
                                                 @RequestParam("oldPassword") String oldPassword,
                                                 @RequestParam("newPassword") String newPassword) {
        String result = authService.changePassword(userId, oldPassword, newPassword);
        return ResponseEntity.ok(result);
    }
}
