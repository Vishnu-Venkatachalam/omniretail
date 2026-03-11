package com.cognizant.omniretail.controller;

import com.cognizant.omniretail.model.Product;
import com.cognizant.omniretail.model.ProductVariant;
import com.cognizant.omniretail.model.enums.ProductStatus;
import com.cognizant.omniretail.repository.ProductVariantRepo;
import com.cognizant.omniretail.service.ProductService;
import com.cognizant.omniretail.service.ProductVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductVariantRepo productVariantRepo;
    private final ProductVariantService variantService;

    // Create Product under a Category
    @PostMapping(
            value = "/categories/{categoryId}/products"
    )
    public ResponseEntity<Product> createProduct(@PathVariable long categoryId,
                                                 @RequestBody Product product) {
        Product created = productService.createProduct(product, categoryId);

                 return ResponseEntity
                .created(URI.create("/api/v1/products/" + created.getProductId()))
                .body(created);

    }

    @PostMapping("/products/{productId}/variants")
    public ResponseEntity<ProductVariant> createVariantUnderProduct(
            @PathVariable Long productId,
            @RequestBody ProductVariant variant
    ) {
        ProductVariant created = variantService.createVariant(productId, variant);
        return ResponseEntity
                .created(URI.create("/api/v1/variants/" + created.getVariantId()))
                .body(created);
    }


    // Get All Products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Get Product by ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }


    @GetMapping("/products/{productId}/variants")
    public ResponseEntity<List<ProductVariant>> getVariantsForProduct(@PathVariable Long productId) {
        List<ProductVariant> variants = productVariantRepo.findByProduct_ProductId(productId);
        return ResponseEntity.ok(variants);
    }


    // Update Product (name, brand, status as per your service)
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestBody Product updatedProduct) {
        Product updated = productService.updateProduct(id, updatedProduct);
        return ResponseEntity.ok(updated);
    }

    // Delete Product
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Change Product Status (Activate / Deactivate)
    @PatchMapping("/products/{id}/status")
    public ResponseEntity<Product> changeProductStatus(@PathVariable Long id,
                                                       @RequestParam("status") ProductStatus status) {
        Product updated = productService.changeProductStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    // Very simple error handling (same pattern you used for Category)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        String msg = ex.getMessage() != null ? ex.getMessage() : "Bad request";
        if (msg.toLowerCase().contains("not found")) {
            return ResponseEntity.status(404).body(msg);
        }
        return ResponseEntity.badRequest().body(msg);
    }
}
