package com.acquirerx.backend.terminal.service;

import com.acquirerx.backend.exception.BadRequestException;
import com.acquirerx.backend.exception.ResourceNotFoundException;
import com.acquirerx.backend.terminal.dto.*;
import com.acquirerx.backend.terminal.entity.*;
import com.acquirerx.backend.terminal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TerminalService {

    @Autowired private TerminalRepository terminalRepository;
    @Autowired private ParamProfileRepository paramProfileRepository;
    @Autowired private TerminalHealthRepository terminalHealthRepository;

    // 1. PROVISION MACHINE & GENERATE KEYS
    public TerminalResponseDTO provisionNewTerminal(TerminalRequestDTO request) {
        Terminal terminal = new Terminal();
        terminal.setStoreId(request.getStoreId());
        terminal.setBrandModel(request.getBrandModel());
        terminal.setCapability(request.getCapability());

        // Generate Identifiers
        terminal.setTid(UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 8));
        terminal.setTmkReference("KEY-SIM-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        terminal.setParamProfileId(request.getParamProfileId());
        terminal.setStatus("PROVISIONED");

        Terminal saved = terminalRepository.save(terminal);

        TerminalResponseDTO response = new TerminalResponseDTO();
        response.setTerminalId(saved.getTerminalId());
        response.setStoreId(saved.getStoreId());
        response.setTid(saved.getTid());
        response.setBrandModel(saved.getBrandModel());
        response.setStatus(saved.getStatus());
        response.setTmkReference(saved.getTmkReference());
        return response;
    }

    // 2. CREATE RULES
    public ParamProfile createProfile(ParamProfileDTO dto) throws BadRequestException {
        if(dto.getName()==null || dto.getName().isEmpty() || dto.getParamsJson()==null){
            throw new BadRequestException("Profile name and rules are required");
        }
        ParamProfile profile = new ParamProfile();
        profile.setName(dto.getName());
        profile.setParamsJson(dto.getParamsJson());
        profile.setVersion(dto.getVersion());
        profile.setStatus("ACTIVE");
        return paramProfileRepository.save(profile);
    }

    // 3. LINK MACHINE TO RULES
    public String assignProfile(String tid, Long profileId) {
        Terminal terminal = terminalRepository.findByTid(tid);
        if (terminal == null) throw new ResourceNotFoundException("Terminal not found: " + tid);
        terminal.setParamProfileId(profileId);
        terminalRepository.save(terminal);
        return "Linked Profile " + profileId + " to TID " + tid;
    }

    // 4. POS BOOT UP DOWNLOAD (Rules + Firmware Version)
    public String getParamsForTerminal(String tid) {
        Terminal terminal = terminalRepository.findByTid(tid);
        if (terminal == null || terminal.getParamProfileId() == null) {
            return "{\"error\": \"No profile assigned.\"}";
        }
        ParamProfile profile = paramProfileRepository.findById(terminal.getParamProfileId())
                .orElseThrow(() -> new ResourceNotFoundException("ParamProfile not found for TID: " + tid));
        // Return both version and JSON rules
        return "{\"version\": \"" + profile.getVersion() + "\", \"rules\": " + profile.getParamsJson() + "}";
    }

    // 5. HEARTBEAT & STATUS FLIP
    public String logHealth(String tid, TerminalHealth health) {
        Terminal terminal = terminalRepository.findByTid(tid);
        if (terminal == null) throw new ResourceNotFoundException("Terminal not found: " + tid);

        health.setTerminalId(terminal.getTerminalId());
        health.setLastSeen(LocalDateTime.now());
        health.setStatus("ONLINE");
        terminalHealthRepository.save(health);

        // Flip terminal status to ACTIVE on first ping
        if ("PROVISIONED".equals(terminal.getStatus())) {
            terminal.setStatus("ACTIVE");
            terminalRepository.save(terminal);
        }
        return "Health Logged.";
    }

    // -----------------------------------------------------------------------
    // Terminal CRUD
    // -----------------------------------------------------------------------

    public List<Terminal> getAllTerminals() {
        return terminalRepository.findAll();
    }

    public Terminal getTerminalByTid(String tid) {
        Terminal terminal = terminalRepository.findByTid(tid);
        if (terminal == null) throw new ResourceNotFoundException("Terminal not found: " + tid);
        return terminal;
    }

    public Terminal updateTerminalStatus(String tid, String status) {
        Terminal terminal = terminalRepository.findByTid(tid);
        if (terminal == null) throw new ResourceNotFoundException("Terminal not found: " + tid);
        terminal.setStatus(status);
        return terminalRepository.save(terminal);
    }

    public void deleteTerminal(String tid) {
        Terminal terminal = terminalRepository.findByTid(tid);
        if (terminal == null) throw new ResourceNotFoundException("Terminal not found: " + tid);
        terminalRepository.delete(terminal);
    }

    // -----------------------------------------------------------------------
    // Param Profile CRUD
    // -----------------------------------------------------------------------

    public List<ParamProfile> getAllProfiles() {
        return paramProfileRepository.findAll();
    }

    public ParamProfile getProfileById(Long profileId) {
        return paramProfileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found: " + profileId));
    }

    public ParamProfile updateProfile(Long profileId, ParamProfileDTO dto) {
        ParamProfile profile = paramProfileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found: " + profileId));
        profile.setName(dto.getName());
        profile.setParamsJson(dto.getParamsJson());
        profile.setVersion(dto.getVersion());
        profile.setStatus(dto.getStatus());
        return paramProfileRepository.save(profile);
    }

    public void deleteProfile(Long profileId) {
        ParamProfile profile = paramProfileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found: " + profileId));
        paramProfileRepository.delete(profile);
    }

    // -----------------------------------------------------------------------
    // Health History
    // -----------------------------------------------------------------------

    public List<TerminalHealth> getHealthHistory(String tid) {
        Terminal terminal = terminalRepository.findByTid(tid);
        if (terminal == null) throw new ResourceNotFoundException("Terminal not found: " + tid);
        return terminalHealthRepository.findByTerminalId(terminal.getTerminalId());
    }
}
