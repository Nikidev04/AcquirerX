package com.acquirerx.backend.store.controller;

import com.acquirerx.backend.store.dto.StoreDTO;
import com.acquirerx.backend.store.entity.Store;
import com.acquirerx.backend.store.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    // POST /api/merchants/{merchantId}/stores
    @PostMapping("/api/merchants/{merchantId}/stores")
    public ResponseEntity<Store> createStore(@PathVariable("merchantId") Long merchantId,
                                             @RequestBody StoreDTO dto) {
        Store created = storeService.createStore(merchantId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/merchants/{merchantId}/stores
    @GetMapping("/api/merchants/{merchantId}/stores")
    public ResponseEntity<List<Store>> getStoresByMerchant(@PathVariable("merchantId") Long merchantId) {
        return ResponseEntity.ok(storeService.getStoresByMerchant(merchantId));
    }

    // GET /api/stores/{storeId}
    @GetMapping("/api/stores/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable("storeId") Long storeId) {
        return ResponseEntity.ok(storeService.getStoreById(storeId));
    }

    // PUT /api/stores/{storeId}
    @PutMapping("/api/stores/{storeId}")
    public ResponseEntity<Store> updateStore(@PathVariable("storeId") Long storeId,
                                             @RequestBody StoreDTO dto) {
        return ResponseEntity.ok(storeService.updateStore(storeId, dto));
    }

    // DELETE /api/stores/{storeId}
    @DeleteMapping("/api/stores/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable("storeId") Long storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.noContent().build();
    }
}
