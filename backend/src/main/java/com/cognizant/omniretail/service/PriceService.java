package com.cognizant.omni.omniretail.service;
import com.cognizant.omni.omniretail.model.Price;
import com.cognizant.omni.omniretail.model.ProductVariant;
import com.cognizant.omni.omniretail.model.Store;
import com.cognizant.omni.omniretail.repository.PriceRepo;
import com.cognizant.omni.omniretail.repository.ProductVariantRepo;
import com.cognizant.omni.omniretail.repository.StoreRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepo priceRepo;
    private final StoreRepo storeRepo;
    private final ProductVariantRepo variantRepo;

    @Transactional
    public Price createPrice(Long storeId, Long variantId, Price input) {

        Store store = storeRepo.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        ProductVariant variant = variantRepo.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        // Validation 1: Date order
        if (input.getEffectiveFrom().isAfter(input.getEffectiveTo())) {
            throw new RuntimeException("effectiveFrom cannot be after effectiveTo");
        }

        // Validation 2: Price values
        if (input.getSellingPrice() > input.getMrp()) {
            throw new RuntimeException("sellingPrice cannot be greater than mrp");
        }

        // Validation 3: Overlap check
        List<Price> overlaps = priceRepo.findOverlappingPrices(
                variantId,
                storeId,
                input.getEffectiveFrom(),
                input.getEffectiveTo()
        );

        if (!overlaps.isEmpty()) {
            throw new RuntimeException("Price already exists in this date range");
        }

        Price newPrice = Price.builder()
                .productVariant(variant)
                .store(store)
                .mrp(input.getMrp())
                .sellingPrice(input.getSellingPrice())
                .effectiveFrom(input.getEffectiveFrom())
                .effectiveTo(input.getEffectiveTo())
                .build();

        return priceRepo.save(newPrice);
    }

    public List<Price> getAllPrices() {
        return priceRepo.findAll();
    }
    @Transactional
    public Price updatePrice(Long priceId, Price input) {

        Price existing = priceRepo.findById(priceId)
                .orElseThrow(() -> new RuntimeException("Price not found"));

        // PUT requires all fields
        if (input.getMrp() == null ||
                input.getSellingPrice() == null ||
                input.getEffectiveFrom() == null ||
                input.getEffectiveTo() == null) {
            throw new RuntimeException("All fields are required for update");
        }

        existing.setMrp(input.getMrp());
        existing.setSellingPrice(input.getSellingPrice());
        existing.setEffectiveFrom(input.getEffectiveFrom());
        existing.setEffectiveTo(input.getEffectiveTo());

        return priceRepo.save(existing);
    }

    @Transactional
    public Price patchPrice(Long priceId, Price input) {

        Price existing = priceRepo.findById(priceId)
                .orElseThrow(() -> new RuntimeException("Price not found"));

        // Only update fields that are present
        if (input.getMrp() != null) existing.setMrp(input.getMrp());
        if (input.getSellingPrice() != null) existing.setSellingPrice(input.getSellingPrice());
        if (input.getEffectiveFrom() != null) existing.setEffectiveFrom(input.getEffectiveFrom());
        if (input.getEffectiveTo() != null) existing.setEffectiveTo(input.getEffectiveTo());

        return priceRepo.save(existing);
    }

    @Transactional
    public void deletePrice(Long priceId) {
        Price existing = priceRepo.findById(priceId)
                .orElseThrow(() -> new RuntimeException("Price not found"));

        priceRepo.delete(existing);
    }
}
