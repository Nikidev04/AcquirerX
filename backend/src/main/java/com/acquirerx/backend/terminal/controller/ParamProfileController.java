package com.acquirerx.backend.terminal.controller;

import com.acquirerx.backend.terminal.dto.ParamProfileDTO;
import com.acquirerx.backend.terminal.entity.ParamProfile;
import com.acquirerx.backend.terminal.service.TerminalService;
import com.acquirerx.backend.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pos/profiles")
public class ParamProfileController {

    @Autowired
    private TerminalService terminalService;

    // Dashboard API: Create new rule sets (e.g. "Supermarket Rules")
    @PostMapping
    public ResponseEntity<ParamProfile> createParamProfile(@RequestBody ParamProfileDTO dto) throws BadRequestException {
        ParamProfile savedProfile = terminalService.createProfile(dto);
        return ResponseEntity.ok(savedProfile);
    }

    // Get all profiles
    @GetMapping
    public ResponseEntity<List<ParamProfile>> getAllProfiles() {
        return ResponseEntity.ok(terminalService.getAllProfiles());
    }

    // Get a single profile by ID
    @GetMapping("/{profileId}")
    public ResponseEntity<ParamProfile> getProfileById(@PathVariable("profileId") Long profileId) {
        return ResponseEntity.ok(terminalService.getProfileById(profileId));
    }

    // Update a profile
    @PutMapping("/{profileId}")
    public ResponseEntity<ParamProfile> updateProfile(@PathVariable("profileId") Long profileId,
                                                       @RequestBody ParamProfileDTO dto) {
        return ResponseEntity.ok(terminalService.updateProfile(profileId, dto));
    }

    // Delete a profile
    @DeleteMapping("/{profileId}")
    public ResponseEntity<String> deleteProfile(@PathVariable("profileId") Long profileId) {
        terminalService.deleteProfile(profileId);
        return ResponseEntity.ok("Profile deleted");
    }
}
