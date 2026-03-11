package com.cognizant.omniretail.controller;

import com.cognizant.omniretail.model.Price;
import com.cognizant.omniretail.model.ProductVariant;
import com.cognizant.omniretail.model.enums.*;
import com.cognizant.omniretail.service.PriceService;
import com.cognizant.omniretail.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService variantService;
    private final PriceService priceService;

    @PostMapping("/variants/{variantId}/stores/{storeId}/price")
    public ResponseEntity<Price> setVariantPrice(
            @PathVariable Long variantId,
            @PathVariable Long storeId,
            @RequestBody Price input
    ) {
        if (input.getEffectiveFrom() == null) {
            input.setEffectiveFrom(LocalDate.now());
        }
        Price created = priceService.createPrice(storeId, variantId, input);
        return ResponseEntity.ok(created);
    }

    // Update Variant by ID
    @PutMapping("/variants/{variantId}")
    public ResponseEntity<ProductVariant> updateVariant(
            @PathVariable Long variantId,
            @RequestBody ProductVariant updatedVariant
    ) {
        ProductVariant saved = variantService.updateVariant(variantId, updatedVariant);
        return ResponseEntity.ok(saved);
    }

    // Get All Variants
    @GetMapping("/variants")
    public ResponseEntity<List<ProductVariant>> getAllVariants() {
        return ResponseEntity.ok(variantService.getAllVariants());
    }

    // Get Variant by ID
    @GetMapping("/variants/{variantId}")
    public ResponseEntity<ProductVariant> getVariantById(@PathVariable Long variantId) {
        return ResponseEntity.ok(variantService.getVariantById(variantId));
    }

    // Get Variant by SKU
    @GetMapping("/variants/sku/{sku}")
    public ResponseEntity<ProductVariant> getVariantBySku(@PathVariable String sku) {
        ProductVariant variant = variantService.getVariantBySku(sku);
        return ResponseEntity.ok(variant);
    }

    @GetMapping("/variants/{variantId}/prices")
    public ResponseEntity<List<Price>> getPricesForVariantSorted(@PathVariable Long variantId) {
        return ResponseEntity.ok(priceService.getPricesForVariantSorted(variantId));
    }

    // Delete Variant by ID
    @DeleteMapping("/variants/{variantId}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long variantId) {
        variantService.deleteVariant(variantId);
        return ResponseEntity.noContent().build();
    }

    // Change Variant Status by ID
    @PatchMapping("/variants/{variantId}/status")
    public ResponseEntity<ProductVariant> changeVariantStatus(
            @PathVariable Long variantId,
            @RequestParam("value") VariantStatus status
    ) {
        ProductVariant updated = variantService.changeVariantStatus(variantId, status);
        return ResponseEntity.ok(updated);
    }

    // Basic Exception Handling
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeExceptions(RuntimeException ex) {
        // Map common messages to 404 or 400 as appropriate
        String msg = ex.getMessage() != null ? ex.getMessage() : "Unexpected error";
        if (msg.toLowerCase().contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        if (msg.toLowerCase().contains("exists") || msg.toLowerCase().contains("empty")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }
}