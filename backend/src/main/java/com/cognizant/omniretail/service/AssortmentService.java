package com.cognizant.omniretail.service;

import com.cognizant.omniretail.model.Assortment;
import com.cognizant.omniretail.model.ProductVariant;
import com.cognizant.omniretail.model.Store;
import com.cognizant.omniretail.model.enums.AssortmentStatus;
import com.cognizant.omniretail.repository.AssortmentRepo;
import com.cognizant.omniretail.repository.ProductVariantRepo;
import com.cognizant.omniretail.repository.StoreRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssortmentService {

    private final AssortmentRepo assortmentRepo;
    private final StoreRepo storeRepo;
    private final ProductVariantRepo variantRepo;

    @Transactional
    public Assortment create(Long storeId, Long variantId, AssortmentStatus status) {
        Store store = storeRepo.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        ProductVariant variant = variantRepo.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        // If already exists, return the existing record (simple behavior)
        var existing = assortmentRepo.findByStore_StoreIdAndProductvariant_VariantId(storeId, variantId);
        if (existing.isPresent()) {
            return existing.get();
        }

        Assortment a = Assortment.builder()
                .store(store)
                .productvariant(variant)
                .status(status != null ? status : AssortmentStatus.LISTED)
                .build();

        return assortmentRepo.save(a);
    }

    public Assortment getById(Long assortmentId) {
        return assortmentRepo.findById(assortmentId)
                .orElseThrow(() -> new RuntimeException("Assortment not found"));
    }

    public List<Assortment> getAll() {
        return assortmentRepo.findAll();
    }

    public List<Assortment> getByStore(Long storeId) {
        return assortmentRepo.findByStore_StoreId(storeId);
    }

    @Transactional
    public Assortment changeStatus(Long assortmentId, AssortmentStatus status) {
        Assortment a = getById(assortmentId);
        a.setStatus(status);
        return assortmentRepo.save(a);
    }

    @Transactional
    public void delete(Long assortmentId) {
        Assortment a = getById(assortmentId);
        assortmentRepo.delete(a);
    }
}