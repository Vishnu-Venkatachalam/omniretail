package com.cognizant.omni.omniretail.service;

import com.cognizant.omni.omniretail.model.Product;
import com.cognizant.omni.omniretail.model.Category;
import com.cognizant.omni.omniretail.model.enums.ProductStatus;
import com.cognizant.omni.omniretail.repository.ProductRepo;
import com.cognizant.omni.omniretail.repository.CategoryRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    //Create Product
    public Product createProduct(Product product, Long categoryId) {

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new RuntimeException("Product name cannot be empty");
        }

        if (product.getBrand() == null || product.getBrand().trim().isEmpty()) {
            throw new RuntimeException("Product brand cannot be empty");
        }

        // Check Category Exists
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

        // Default status if null
        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.ACTIVE);
        }

        return productRepo.save(product);
    }

    //Update Product
    public Product updateProduct(Long id, Product updatedProduct) {

        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(updatedProduct.getName());
        existing.setBrand(updatedProduct.getBrand());
        existing.setStatus(updatedProduct.getStatus());

        return productRepo.save(existing);
    }

    //Get All Products
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    //Get Product By id
    public Product getProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    //Delete Product
    public void deleteProduct(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Because of CascadeType.ALL,
        // deleting product deletes its variants automatically
        productRepo.delete(product);
    }

    //Activate / Deactivate Product
    public Product changeProductStatus(Long id, ProductStatus status) {

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStatus(status);

        return productRepo.save(product);
    }
}
