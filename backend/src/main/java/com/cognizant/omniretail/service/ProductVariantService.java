package com.cognizant.omni.omniretail.service;

import com.cognizant.omni.omniretail.model.Product;
import com.cognizant.omni.omniretail.model.ProductVariant;
import com.cognizant.omni.omniretail.repository.ProductRepo;
import com.cognizant.omni.omniretail.repository.ProductVariantRepo;
import com.cognizant.omni.omniretail.repository.ProductVariantRepo;
import com.cognizant.omni.omniretail.model.enums.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantService {

    private final ProductVariantRepo variantRepo;
    private final ProductRepo productRepo;

    private String generateSimpleSku() {
        long count = variantRepo.count();  // total rows in DB
        return "SKU-" + (count + 1);
    }

    //Create Variant
    public ProductVariant createVariant(Long productId, ProductVariant variant) {

        // Check product exists
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (variant.getSize() == null || variant.getSize().trim().isEmpty()) {
            throw new RuntimeException("Size cannot be empty");
        }

        if (variant.getColor() == null || variant.getColor().trim().isEmpty()) {
            throw new RuntimeException("Color cannot be empty");
        }
        if (variantRepo.existsByProduct_ProductIdAndColorIgnoreCaseAndSizeIgnoreCase(
                productId,
                variant.getColor().trim(),
                variant.getSize().trim()
        )) {
            throw new RuntimeException("Variant with same color and size already exists");
        }

        var existingVariant = variantRepo
                .findByProduct_ProductIdAndColorIgnoreCaseAndSizeIgnoreCase(productId, variant.getColor(), variant.getSize());

        if (existingVariant.isPresent()) {
            return existingVariant.get();
        }

        // Auto-generate SKU (ignore user‑entered one)
        String sku = generateSimpleSku();
        variant.setSku(sku);

        // Set relationship
        variant.setProduct(product);

        // Default status
        if (variant.getStatus() == null) {
            variant.setStatus(VariantStatus.ACTIVE);
        }

        return variantRepo.save(variant);
    }

    //Update Variant
    public ProductVariant updateVariant(Long variantId, ProductVariant updatedVariant) {

        ProductVariant existing = variantRepo.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        existing.setSize(updatedVariant.getSize());
        existing.setColor(updatedVariant.getColor());
        existing.setStatus(updatedVariant.getStatus());

        return variantRepo.save(existing);
    }

    //Get All Variants
    public List<ProductVariant> getAllVariants() {
        return variantRepo.findAll();
    }

    //Get Variant By id
    public ProductVariant getVariantById(Long id) {
        return variantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Variant not found"));
    }

    //Delete Variant
    public void deleteVariant(Long id) {

        ProductVariant variant = variantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        variantRepo.delete(variant);
    }

    //find by sku
    public ProductVariant getVariantBySku(String sku) {
        return variantRepo.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Variant not found"));
    }


    //Change Variant Status
    public ProductVariant changeVariantStatus(Long id, VariantStatus status) {

        ProductVariant variant = variantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        variant.setStatus(status);

        return variantRepo.save(variant);
    }
}