package com.acquirerx.backend.merchant.controller;

import com.acquirerx.backend.merchant.dto.KYCRequestDTO;
import com.acquirerx.backend.merchant.dto.MerchantRequestDTO;
import com.acquirerx.backend.merchant.dto.PricingModelDTO;
import com.acquirerx.backend.merchant.dto.SettlementProfileDTO;
import com.acquirerx.backend.merchant.entity.Merchant;
import com.acquirerx.backend.merchant.entity.MerchantKYC;
import com.acquirerx.backend.merchant.entity.PricingModel;
import com.acquirerx.backend.merchant.entity.SettlementProfile;
import com.acquirerx.backend.merchant.service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping
    public ResponseEntity<Merchant> createMerchant(@RequestBody MerchantRequestDTO dto) {
        return ResponseEntity.ok(merchantService.createMerchant(dto));
    }

    @GetMapping
    public ResponseEntity<List<Merchant>> getAllMerchants() {
        return ResponseEntity.ok(merchantService.getAllMerchants());
    }

    // This allows you to do GET http://localhost:9090/api/merchants/1
    @GetMapping("/{id}")
    public ResponseEntity<Merchant> getMerchantById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(merchantService.getMerchantById(id));
    }

    /**
     * API Endpoint to upload KYC documents for a specific merchant.
     * Usage: POST /api/merchants/{id}/kyc
     * * @param id The Merchant ID from the URL path
     * @param dto The JSON body containing document details
     * @return The created KYC record
     */
    @PostMapping("/{id}/kyc")
    public ResponseEntity<MerchantKYC> uploadKYC(@PathVariable("id") Long id, @RequestBody KYCRequestDTO dto) {
        return ResponseEntity.ok(merchantService.uploadKYC(id, dto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Merchant>> searchMerchants(@RequestParam("name") String name) {
        return ResponseEntity.ok(merchantService.searchByName(name));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Merchant>> filterMerchants(@RequestParam("status") com.acquirerx.backend.common.enums.Status status) {
        return ResponseEntity.ok(merchantService.filterByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Merchant> updateMerchant(@PathVariable("id") Long id, @RequestBody MerchantRequestDTO dto) {
        return ResponseEntity.ok(merchantService.updateMerchant(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchant(@PathVariable("id") Long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }

    // -----------------------------------------------------------------------
    // KYC
    // -----------------------------------------------------------------------

    @GetMapping("/{id}/kyc")
    public ResponseEntity<MerchantKYC> getKycByMerchant(@PathVariable("id") Long id) {
        return ResponseEntity.ok(merchantService.getKycByMerchant(id));
    }

    // -----------------------------------------------------------------------
    // Settlement Profile
    // -----------------------------------------------------------------------

    @GetMapping("/{id}/settlement")
    public ResponseEntity<SettlementProfile> getSettlementByMerchant(@PathVariable("id") Long id) {
        return ResponseEntity.ok(merchantService.getSettlementByMerchant(id));
    }

    @PutMapping("/{id}/settlement")
    public ResponseEntity<SettlementProfile> updateSettlement(@PathVariable("id") Long id,
                                                               @RequestBody SettlementProfileDTO dto) {
        return ResponseEntity.ok(merchantService.updateSettlement(id, dto));
    }

    // -----------------------------------------------------------------------
    // Pricing Model
    // -----------------------------------------------------------------------

    @GetMapping("/{id}/pricing")
    public ResponseEntity<List<PricingModel>> getPricingByMerchant(@PathVariable("id") Long id) {
        return ResponseEntity.ok(merchantService.getPricingByMerchant(id));
    }

    @PostMapping("/{id}/pricing")
    public ResponseEntity<PricingModel> addPricingModel(@PathVariable("id") Long id,
                                                         @RequestBody PricingModelDTO dto) {
        return ResponseEntity.ok(merchantService.addPricingModel(id, dto));
    }

    @PutMapping("/{id}/pricing/{pricingId}")
    public ResponseEntity<PricingModel> updatePricingModel(@PathVariable("id") Long id,
                                                            @PathVariable("pricingId") Long pricingId,
                                                            @RequestBody PricingModelDTO dto) {
        return ResponseEntity.ok(merchantService.updatePricingModel(id, pricingId, dto));
    }
}
