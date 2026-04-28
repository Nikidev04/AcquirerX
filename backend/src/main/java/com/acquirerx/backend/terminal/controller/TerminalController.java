package com.acquirerx.backend.terminal.controller;

import com.acquirerx.backend.terminal.dto.TerminalRequestDTO;
import com.acquirerx.backend.terminal.dto.TerminalResponseDTO;
import com.acquirerx.backend.terminal.entity.Terminal;
import com.acquirerx.backend.terminal.entity.TerminalHealth;
import com.acquirerx.backend.terminal.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pos/terminals")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    // 1. Dashboard API: Create a new machine
    @PostMapping
    public ResponseEntity<TerminalResponseDTO> provisionTerminal(@RequestBody TerminalRequestDTO request) {
        TerminalResponseDTO response = terminalService.provisionNewTerminal(request);
        return ResponseEntity.ok(response);
    }

    // 2. Dashboard API: Link rules to a machine
    @PutMapping("/{tid}/profile/{profileId}")
    public ResponseEntity<String> assignProfileToTerminal(
            @PathVariable("tid") String tid,
            @PathVariable("profileId") Long profileId) {
        String result = terminalService.assignProfile(tid, profileId);
        return ResponseEntity.ok(result);
    }

    // 3. POS API: Machine asks for its rules when turned on
    @GetMapping("/{tid}/download-params")
    public ResponseEntity<String> downloadTerminalParams(@PathVariable("tid") String tid) {
        String paramsJson = terminalService.getParamsForTerminal(tid);
        return ResponseEntity.ok(paramsJson);
    }

    // 4. POS API: Machine sends its battery/signal status
    @PostMapping("/{tid}/health-ping")
    public ResponseEntity<String> logTerminalHealth(
            @PathVariable("tid") String tid,
            @RequestBody TerminalHealth healthData) {
        String result = terminalService.logHealth(tid, healthData);
        return ResponseEntity.ok(result);
    }

    // 5. Get all terminals
    @GetMapping
    public ResponseEntity<List<Terminal>> getAllTerminals() {
        return ResponseEntity.ok(terminalService.getAllTerminals());
    }

    // 6. Get a single terminal by TID
    @GetMapping("/{tid}")
    public ResponseEntity<Terminal> getTerminalByTid(@PathVariable("tid") String tid) {
        return ResponseEntity.ok(terminalService.getTerminalByTid(tid));
    }

    // 7. Update terminal status
    @PutMapping("/{tid}/status")
    public ResponseEntity<Terminal> updateTerminalStatus(
            @PathVariable("tid") String tid,
            @RequestParam("status") String status) {
        return ResponseEntity.ok(terminalService.updateTerminalStatus(tid, status));
    }

    // 8. Delete a terminal
    @DeleteMapping("/{tid}")
    public ResponseEntity<String> deleteTerminal(@PathVariable("tid") String tid) {
        terminalService.deleteTerminal(tid);
        return ResponseEntity.ok("Terminal removed");
    }

    // 9. Get health history for a terminal
    @GetMapping("/{tid}/health-history")
    public ResponseEntity<List<TerminalHealth>> getHealthHistory(@PathVariable("tid") String tid) {
        return ResponseEntity.ok(terminalService.getHealthHistory(tid));
    }
}
