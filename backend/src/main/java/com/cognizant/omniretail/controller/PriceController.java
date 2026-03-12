package com.cognizant.omniretail.controller;

import com.cognizant.omniretail.model.Price;
import com.cognizant.omniretail.service.PriceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
@SecurityRequirement(name="bearerAuth")
public class PriceController {

    private final PriceService priceService;


    @PostMapping("/store/{storeId}/variant/{variantId}")
    public ResponseEntity<?> createPrice(
            @PathVariable Long storeId,
            @PathVariable Long variantId,
            @RequestBody Price priceInput
    ) {
        try {
            Price saved = priceService.createPrice(storeId, variantId, priceInput);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<Price>> getAllPrices() {
        return ResponseEntity.ok(priceService.getAllPrices());
    }

    @PutMapping("/{priceId}")
    public ResponseEntity<?> updatePrice(
            @PathVariable Long priceId,
            @RequestBody Price priceInput
    ) {
        try {
            Price updated = priceService.updatePrice(priceId, priceInput);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{priceId}")
    public ResponseEntity<?> deletePrice(@PathVariable Long priceId) {
        try {
            priceService.deletePrice(priceId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


}
