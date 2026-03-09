package com.cognizant.omni.omniretail.controller;

import com.cognizant.omni.omniretail.model.Promotion;
import com.cognizant.omni.omniretail.model.enums.PromotionStatus;
import com.cognizant.omni.omniretail.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    // Create
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Promotion body) {
        try {
            Promotion saved = promotionService.create(body);
            return ResponseEntity
                    .created(URI.create("/api/v1/promotions/" + saved.getPromotionId()))
                    .body(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Get all
    @GetMapping
    public ResponseEntity<List<Promotion>> getAll() {
        return ResponseEntity.ok(promotionService.getAll());
    }

    // Get by id
    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(promotionService.getById(id));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Promotion body) {
        try {
            Promotion updated = promotionService.update(id, body);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Change status only (PATCH)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Promotion> changeStatus(
            @PathVariable Long id,
            @RequestParam("value") PromotionStatus status
    ) {
        return ResponseEntity.ok(promotionService.changeStatus(id, status));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        promotionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Basic exception mapping
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        String msg = ex.getMessage() != null ? ex.getMessage() : "Unexpected error";
        if (msg.toLowerCase().contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        if (msg.toLowerCase().contains("exists")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }
}