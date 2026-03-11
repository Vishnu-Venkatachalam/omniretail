package com.cognizant.omniretail.service;

import com.cognizant.omniretail.model.Product;
import com.cognizant.omniretail.model.Category;
import com.cognizant.omniretail.model.ProductVariant;
import com.cognizant.omniretail.model.enums.ProductStatus;
import com.cognizant.omniretail.model.enums.VariantStatus;
import com.cognizant.omniretail.repository.ProductRepo;
import com.cognizant.omniretail.repository.CategoryRepo;

import com.cognizant.omniretail.repository.ProductVariantRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    private final ProductVariantService variantService;
    private final ProductVariantRepo productVariantRepo;


    //Create Product
    public Product createProduct(Product product, Long categoryId) {

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new RuntimeException("Product name cannot be empty");
        }

        if (product.getBrand() == null || product.getBrand().trim().isEmpty()) {
            throw new RuntimeException("Product brand cannot be empty");
        }
        if (productRepo.existsByNameAndBrand(product.getName().trim(), product.getBrand().trim())) {
            throw new RuntimeException("Product with same name and brand already exists");
        }

        // Check Category Exists
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

        // Default status if null
        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.ACTIVE);
        }

        List<ProductVariant> incomingVariants =
                (product.getVariants() != null) ? new ArrayList<>(product.getVariants()) : List.of();
        product.setVariants(null);

        Product saved = productRepo.save(product);

        for (ProductVariant v : incomingVariants) {
            // Do not trust incoming SKU; createVariant() will validate and generate SKU
            ProductVariant toCreate = new ProductVariant();
            toCreate.setColor(v.getColor());
            toCreate.setSize(v.getSize());
            toCreate.setStatus(v.getStatus() != null ? v.getStatus() : VariantStatus.ACTIVE);

            variantService.createVariant(saved.getProductId(), toCreate);
        }
        saved.setVariants(productVariantRepo.findByProduct_ProductId(saved.getProductId()));        return saved;
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
