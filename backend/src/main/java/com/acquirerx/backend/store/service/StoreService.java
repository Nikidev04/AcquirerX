package com.acquirerx.backend.store.service;

import com.acquirerx.backend.exception.ResourceNotFoundException;
import com.acquirerx.backend.merchant.entity.Merchant;
import com.acquirerx.backend.merchant.repository.MerchantRepository;
import com.acquirerx.backend.store.dto.StoreDTO;
import com.acquirerx.backend.store.entity.Store;
import com.acquirerx.backend.store.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final MerchantRepository merchantRepository;

    public StoreService(StoreRepository storeRepository, MerchantRepository merchantRepository) {
        this.storeRepository = storeRepository;
        this.merchantRepository = merchantRepository;
    }

    public Store createStore(Long merchantId, StoreDTO dto) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Merchant not found: " + merchantId));

        Store store = new Store();
        store.setMerchant(merchant);
        store.setAddress(dto.getAddress());
        store.setStatus(dto.getStatus());

        return storeRepository.save(store);
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found: " + id));
    }

    public List<Store> getStoresByMerchant(Long merchantId) {
        return storeRepository.findByMerchant_MerchantID(merchantId);
    }

    public Store updateStore(Long id, StoreDTO dto) {
        Store store = getStoreById(id);
        store.setAddress(dto.getAddress());
        store.setStatus(dto.getStatus());
        return storeRepository.save(store);
    }

    public void deleteStore(Long id) {
        Store store = getStoreById(id);
        storeRepository.deleteById(store.getStoreID());
    }
}
