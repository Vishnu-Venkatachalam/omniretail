package com.cognizant.omniretail.repository;

import com.cognizant.omniretail.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionRepo extends JpaRepository<Promotion, Long> {

    Optional<Promotion> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

}
