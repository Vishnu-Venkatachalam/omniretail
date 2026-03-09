package com.cognizant.omni.omniretail.controller;

import com.cognizant.omni.omniretail.model.Product;
import com.cognizant.omni.omniretail.model.enums.ProductStatus;
import com.cognizant.omni.omniretail.service.ProductService;
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
