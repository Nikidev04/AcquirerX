package com.acquirerx.backend.switchmodule.service;

import com.acquirerx.backend.exception.BadRequestException;
import com.acquirerx.backend.exception.ResourceNotFoundException;
import com.acquirerx.backend.switchmodule.dto.AuthRequestDTO;
import com.acquirerx.backend.switchmodule.dto.AuthResponseDTO;
import com.acquirerx.backend.switchmodule.entity.AuthMessage;
import com.acquirerx.backend.switchmodule.entity.Batch;
import com.acquirerx.backend.switchmodule.repository.AuthMessageRepository;
import com.acquirerx.backend.switchmodule.repository.BatchRepository;
import com.acquirerx.backend.switchmodule.util.AuthCodeGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthorizationService {

    private final AuthMessageRepository authMessageRepository;
    private final BatchRepository batchRepository;

    public AuthorizationService(AuthMessageRepository authMessageRepository,
                                BatchRepository batchRepository) {
        this.authMessageRepository = authMessageRepository;
        this.batchRepository = batchRepository;
    }

    public AuthResponseDTO authorize(AuthRequestDTO request) {

        AuthMessage txn = new AuthMessage();

        txn.setTerminalId(request.getTerminalId());
        txn.setMerchantId(request.getMerchantId());
        txn.setAmount(request.getAmount());
        txn.setCurrency(request.getCurrency());
        txn.setTxnType(request.getTxnType());
        txn.setNetwork(request.getNetwork());
        txn.setTxnTime(LocalDateTime.now());

        txn.setPanMasked("XXXX-XXXX-1234");

        AuthResponseDTO response = new AuthResponseDTO();

        if (request.getAmount() <= 10000) {
            response.setResponseCode("00");
            response.setStatus("APPROVED");
        } else {
            response.setResponseCode("05");
            response.setStatus("DECLINED");
        }

        String authCode = AuthCodeGenerator.generateAuthCode();
        response.setAuthCode(authCode);

        txn.setAuthCode(authCode);
        txn.setResponseCode(response.getResponseCode());
        txn.setStatus(response.getStatus());

        authMessageRepository.save(txn);

        return response;
    }

    public void voidTransaction(Long authId) {
        AuthMessage txn = authMessageRepository.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + authId));
        if ("VOIDED".equals(txn.getStatus())) {
            throw new BadRequestException("Transaction already voided");
        }
        txn.setStatus("VOIDED");
        authMessageRepository.save(txn);
    }

    public void refundTransaction(Long authId) {
        AuthMessage original = authMessageRepository.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + authId));
        if ("DECLINED".equals(original.getStatus())) {
            throw new BadRequestException("Cannot refund a declined transaction");
        }

        AuthMessage refund = new AuthMessage();
        refund.setTerminalId(original.getTerminalId());
        refund.setMerchantId(original.getMerchantId());
        refund.setAmount(original.getAmount());
        refund.setCurrency(original.getCurrency());
        refund.setNetwork(original.getNetwork());
        refund.setTxnType("Refund");
        refund.setPanMasked("XXXX-XXXX-1234");
        refund.setTxnTime(LocalDateTime.now());
        refund.setStatus("APPROVED");
        refund.setResponseCode("00");
        refund.setAuthCode(AuthCodeGenerator.generateAuthCode());

        authMessageRepository.save(refund);
    }

    public AuthMessage getTransactionById(Long authId) {
        return authMessageRepository.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + authId));
    }

    public List<AuthMessage> getTransactionsByTerminal(Long terminalId) {
        return authMessageRepository.findByTerminalId(terminalId);
    }

    public List<AuthMessage> getTransactionsByMerchant(Long merchantId) {
        return authMessageRepository.findByMerchantId(merchantId);
    }

    public List<AuthMessage> getTransactionsByStatus(String status) {
        return authMessageRepository.findByStatus(status);
    }

    public Batch openBatch(Long terminalId) {
        Batch batch = new Batch();
        batch.setTerminalId(terminalId);
        batch.setOpenTime(LocalDateTime.now());
        batch.setStatus("Open");
        return batchRepository.save(batch);
    }

    public Batch closeBatch(Long batchId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found: " + batchId));
        if ("Closed".equals(batch.getStatus())) {
            throw new BadRequestException("Batch already closed");
        }
        batch.setCloseTime(LocalDateTime.now());
        batch.setStatus("Closed");
        return batchRepository.save(batch);
    }

    public Batch getBatchById(Long batchId) {
        return batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found: " + batchId));
    }

    public List<Batch> getBatchesByTerminal(Long terminalId) {
        return batchRepository.findByTerminalId(terminalId);
    }
}
