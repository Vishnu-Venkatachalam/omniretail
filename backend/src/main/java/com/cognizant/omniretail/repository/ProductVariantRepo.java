package com.cognizant.omniretail.repository;

import com.cognizant.omniretail.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepo extends JpaRepository<ProductVariant, Long> {
    Optional<ProductVariant> findBySku(String sku);

    Optional<ProductVariant> findByProduct_ProductIdAndColorIgnoreCaseAndSizeIgnoreCase(
            Long productId, String color, String size
    );
    List<ProductVariant> findByProduct_ProductId(Long productId);
    boolean existsByProduct_ProductIdAndColorIgnoreCaseAndSizeIgnoreCase(Long productId, String color, String size);

}
