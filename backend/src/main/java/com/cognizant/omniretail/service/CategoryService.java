package com.cognizant.omni.omniretail.service;

import com.cognizant.omni.omniretail.model.Category;
import com.cognizant.omni.omniretail.repository.CategoryRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;

    //Create Main Category
    public Category createCategory(Category category) {

        // Business Logic 1: Name should not be empty
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new RuntimeException("Category name cannot be empty");
        }

        if (categoryRepo.existsByName(category.getName())) {
            throw new RuntimeException("Category already exists");
        }


        // Business Logic 2: Parent should be null for main category
        category.setParentCategory(null);

        return categoryRepo.save(category);
    }

    //Create Sub Category
    public Category createSubCategory(Long parentId, Category subCategory) {

        // Check parent exists
        Category parent = categoryRepo.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent Category not found"));

        // Business Logic: SubCategory name validation
        if (subCategory.getName() == null || subCategory.getName().trim().isEmpty()) {
            throw new RuntimeException("Sub Category name cannot be empty");
        }

        if (categoryRepo.existsByName(subCategory.getName())) {
            throw new RuntimeException("Sub Category already exists");
        }

        // Set relationship
        subCategory.setParentCategory(parent);

        return categoryRepo.save(subCategory);
    }

    //Get All Categories
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    //Get Category By id
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    //Delete Category
    public void deleteCategory(Long id) {

        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Because of CascadeType.ALL
        // If parent is deleted, children also deleted automatically
        categoryRepo.delete(category);
    }

    //Get the list of parent Category
    public List<Category> getParentCategoryList() {
        return categoryRepo.findByParentCategoryIsNull();
    }

    // Update Category
    public Category updateCategory(Long id, Category updatedCategory) {

        // Check existing category
        Category existing = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Validate name
        if (updatedCategory.getName() == null || updatedCategory.getName().trim().isEmpty()) {
            throw new RuntimeException("Category name cannot be empty");
        }

        // Update fields
        existing.setName(updatedCategory.getName());

        // Save updated category
        return categoryRepo.save(existing);
    }

}
