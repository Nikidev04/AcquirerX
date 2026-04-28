package com.acquirerx.backend.merchant.service;

import com.acquirerx.backend.common.enums.Status;
import com.acquirerx.backend.exception.ResourceNotFoundException;
import com.acquirerx.backend.merchant.dto.KYCRequestDTO;
import com.acquirerx.backend.merchant.dto.MerchantRequestDTO;
import com.acquirerx.backend.merchant.dto.PricingModelDTO;
import com.acquirerx.backend.merchant.dto.SettlementProfileDTO;
import com.acquirerx.backend.merchant.entity.Merchant;
import com.acquirerx.backend.merchant.entity.MerchantKYC;
import com.acquirerx.backend.merchant.entity.PricingModel;
import com.acquirerx.backend.merchant.entity.SettlementProfile;
import com.acquirerx.backend.merchant.repository.MerchantKYCRepository;
import com.acquirerx.backend.merchant.repository.MerchantRepository;
import com.acquirerx.backend.merchant.repository.PricingModelRepository;
import com.acquirerx.backend.merchant.repository.SettlementProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final MerchantKYCRepository kycRepository;
    private final SettlementProfileRepository settlementProfileRepository;
    private final PricingModelRepository pricingModelRepository;

    public MerchantService(MerchantRepository merchantRepository,
                           MerchantKYCRepository kycRepository,
                           SettlementProfileRepository settlementProfileRepository,
                           PricingModelRepository pricingModelRepository) {
        this.merchantRepository = merchantRepository;
        this.kycRepository = kycRepository;
        this.settlementProfileRepository = settlementProfileRepository;
        this.pricingModelRepository = pricingModelRepository;
    }

    @Transactional
    public Merchant createMerchant(MerchantRequestDTO dto) {
        // 1. Create Merchant Entity
        Merchant merchant = Merchant.builder()
                .legalName(dto.getLegalName())
                .doingBusinessAs(dto.getDoingBusinessAs())
                .mcc(dto.getMcc())
                .contactInfo(dto.getContactInfo())
                .riskLevel(dto.getRiskLevel())
                .status(dto.getStatus())
                .build();

        // 2. Attach Settlement Profile
        SettlementProfile profile = SettlementProfile.builder()
                .settlementCycle(dto.getSettlementCycle())
                .bankAccountNumber(dto.getBankAccountNumber())
                .swiftCode(dto.getSwiftCode())
                .merchant(merchant)
                .build();
        merchant.setSettlementProfile(profile);

        // 3. Attach Initial Pricing Model
        PricingModel pricing = PricingModel.builder()
                .pricingType(dto.getPricingType())
                .percentage(dto.getPercentage())
                .baseFee(dto.getBaseFee())
                .merchant(merchant)
                .build();
        merchant.setPricingModels(java.util.List.of(pricing));

        return merchantRepository.save(merchant);
    }

    public Merchant getMerchantById(Long id) {
        return merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchant not found with id: " + id));
    }

    /**
     * Links a KYC document to a merchant and activates the merchant.
     */
    @Transactional
    public MerchantKYC uploadKYC(Long merchantId, KYCRequestDTO dto) {
        // 1. Find the merchant
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        // 2. Create KYC record (Matches the Entity names now)
        MerchantKYC kyc = MerchantKYC.builder()
                .documentType(dto.getDocumentType())
                .documentReference(dto.getDocumentReference())
                .verifiedDate(LocalDateTime.now()) // Records today's date
                .verificationStatus(Status.VERIFIED) // Sets it to Verified
                .merchant(merchant)
                .build();

        // 3. AUTO-ACTIVATION: Change merchant status to ACTIVE
        merchant.setStatus(Status.ACTIVE);
        merchantRepository.save(merchant);

        return kycRepository.save(kyc);
    }

    public List<Merchant> getAllMerchants() {
        return merchantRepository.findAll();
    }

    /**
     * Searches for merchants by name.
     * @param name The partial or full legal name
     */
    public List<Merchant> searchByName(String name) {
        return merchantRepository.findByLegalNameContainingIgnoreCase(name);
    }

    /**
     * Filters merchants by their current status.
     * @param status The status to filter by (e.g., PENDING)
     */
    public List<Merchant> filterByStatus(com.acquirerx.backend.common.enums.Status status) {
        return merchantRepository.findByStatus(status);
    }

    @Transactional
    public Merchant updateMerchant(Long id, MerchantRequestDTO dto) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Merchant not found with id: " + id));

        merchant.setLegalName(dto.getLegalName());
        merchant.setDoingBusinessAs(dto.getDoingBusinessAs());
        merchant.setContactInfo(dto.getContactInfo());
        merchant.setMcc(dto.getMcc());
        merchant.setRiskLevel(dto.getRiskLevel());
        // Note: We usually don't update status here; that's handled by KYC

        return merchantRepository.save(merchant);
    }

    @Transactional
    public void deleteMerchant(Long id) {
        if (!merchantRepository.existsById(id)) {
            throw new RuntimeException("Merchant not found with id: " + id);
        }
        merchantRepository.deleteById(id);
    }

    // -----------------------------------------------------------------------
    // KYC
    // -----------------------------------------------------------------------

    public MerchantKYC getKycByMerchant(Long merchantId) {
        return kycRepository.findByMerchant_MerchantID(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("KYC not found for merchant: " + merchantId));
    }

    // -----------------------------------------------------------------------
    // Settlement Profile
    // -----------------------------------------------------------------------

    public SettlementProfile getSettlementByMerchant(Long merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with id: " + merchantId));
        SettlementProfile settlement = merchant.getSettlementProfile();
        if (settlement == null) {
            throw new ResourceNotFoundException("Settlement profile not found for merchant: " + merchantId);
        }
        return settlement;
    }

    @Transactional
    public SettlementProfile updateSettlement(Long merchantId, SettlementProfileDTO dto) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with id: " + merchantId));
        SettlementProfile settlement = merchant.getSettlementProfile();
        if (settlement == null) {
            throw new ResourceNotFoundException("Settlement profile not found for merchant: " + merchantId);
        }
        settlement.setSettlementCycle(dto.getSettlementCycle());
        settlement.setBankAccountNumber(dto.getBankAccountNumber());
        settlement.setSwiftCode(dto.getSwiftCode());
        return settlementProfileRepository.save(settlement);
    }

    // -----------------------------------------------------------------------
    // Pricing Model
    // -----------------------------------------------------------------------

    public List<PricingModel> getPricingByMerchant(Long merchantId) {
        return pricingModelRepository.findByMerchant_MerchantID(merchantId);
    }

    @Transactional
    public PricingModel addPricingModel(Long merchantId, PricingModelDTO dto) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with id: " + merchantId));
        PricingModel pricing = new PricingModel();
        pricing.setPricingType(dto.getPricingType());
        pricing.setPercentage(dto.getPercentage());
        pricing.setBaseFee(dto.getBaseFee());
        pricing.setMerchant(merchant);
        return pricingModelRepository.save(pricing);
    }

    @Transactional
    public PricingModel updatePricingModel(Long merchantId, Long pricingId, PricingModelDTO dto) {
        // Ensure the merchant exists
        merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found with id: " + merchantId));
        PricingModel pricing = pricingModelRepository.findById(pricingId)
                .orElseThrow(() -> new ResourceNotFoundException("Pricing model not found with id: " + pricingId));
        pricing.setPricingType(dto.getPricingType());
        pricing.setPercentage(dto.getPercentage());
        pricing.setBaseFee(dto.getBaseFee());
        return pricingModelRepository.save(pricing);
    }
}
