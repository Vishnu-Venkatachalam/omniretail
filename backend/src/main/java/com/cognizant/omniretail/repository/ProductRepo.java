package com.cognizant.omni.omniretail.repository;

import com.cognizant.omni.omniretail.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
    boolean existsByNameAndBrand(String name, String brand);
}
