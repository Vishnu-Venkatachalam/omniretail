package com.cognizant.omni.omniretail.controller;

import com.cognizant.omni.omniretail.model.Category;
import com.cognizant.omni.omniretail.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Create Main Category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category created = categoryService.createCategory(category);
        return ResponseEntity.created(URI.create("/api/v1/categories/" + created.getCategoryId())).body(created);
    }

    // Create Sub Category
    @PostMapping("/{parentId}/subcategories")
    public ResponseEntity<Category> createSubCategory(@PathVariable Long parentId,
                                                      @RequestBody Category subCategory) {
        Category created = categoryService.createSubCategory(parentId, subCategory);
        return ResponseEntity.created(URI.create("/api/v1/categories/" + created.getCategoryId())).body(created);
    }

    // Get All Categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Get Category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    // Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // Get Parent (Top-level) Categories
    @GetMapping("/parents")
    public ResponseEntity<List<Category>> getParentCategoryList() {
        return ResponseEntity.ok(categoryService.getParentCategoryList());
    }

    // Update Category (simple: updates name only, as per your service)
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
                                                   @RequestBody Category updatedCategory) {
        Category updated = categoryService.updateCategory(id, updatedCategory);
        return ResponseEntity.ok(updated);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        // You can map specific messages to 404s if you want. Keeping it simple:
        String msg = ex.getMessage() != null ? ex.getMessage() : "Bad request";
        if (msg.toLowerCase().contains("not found")) {
            return ResponseEntity.status(404).body(msg);
        }
        return ResponseEntity.badRequest().body(msg);
    }
}
