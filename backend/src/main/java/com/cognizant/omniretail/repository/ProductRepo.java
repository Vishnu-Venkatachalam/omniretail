package com.cognizant.omniretail.repository;

import com.cognizant.omniretail.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
    boolean existsByNameAndBrand(String name, String brand);
}
