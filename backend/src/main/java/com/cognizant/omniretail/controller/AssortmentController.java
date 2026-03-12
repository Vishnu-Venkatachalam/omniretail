package com.cognizant.omniretail.controller;

import com.cognizant.omniretail.model.Assortment;
import com.cognizant.omniretail.model.enums.AssortmentStatus;
import com.cognizant.omniretail.service.AssortmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/assortments")
@RequiredArgsConstructor
@SecurityRequirement(name="bearerAuth")

public class AssortmentController {

    private final AssortmentService assortmentService;

    // Create: POST /api/v1/assortments/stores/{storeId}/variants/{variantId}?status=ACTIVE
    @PostMapping("/stores/{storeId}/variants/{variantId}")
    public ResponseEntity<Assortment> create(
            @PathVariable Long storeId,
            @PathVariable Long variantId,
            @RequestParam(name = "status", required = false) AssortmentStatus status
    ) {
        Assortment saved = assortmentService.create(storeId, variantId, status);
        return ResponseEntity
                .created(URI.create("/api/v1/assortments/" + saved.getAssortmentId()))
                .body(saved);
    }

    // Get all
    @GetMapping
    public ResponseEntity<List<Assortment>> getAll() {
        return ResponseEntity.ok(assortmentService.getAll());
    }

    // Get by id
    @GetMapping("/{assortmentId}")
    public ResponseEntity<Assortment> getOne(@PathVariable Long assortmentId) {
        return ResponseEntity.ok(assortmentService.getById(assortmentId));
    }

    // Get all assortments for a store
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<List<Assortment>> getByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(assortmentService.getByStore(storeId));
    }

    // Change status
    @PatchMapping("/{assortmentId}/status")
    public ResponseEntity<Assortment> changeStatus(
            @PathVariable Long assortmentId,
            @RequestParam("value") AssortmentStatus status
    ) {
        return ResponseEntity.ok(assortmentService.changeStatus(assortmentId, status));
    }

    // Delete
    @DeleteMapping("/{assortmentId}")
    public ResponseEntity<Void> delete(@PathVariable Long assortmentId) {
        assortmentService.delete(assortmentId);
        return ResponseEntity.noContent().build();
    }

    // Basic error mapping (optional)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        String msg = ex.getMessage() != null ? ex.getMessage() : "Unexpected error";
        if (msg.toLowerCase().contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        if (msg.toLowerCase().contains("exists") || msg.toLowerCase().contains("duplicate")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }
}