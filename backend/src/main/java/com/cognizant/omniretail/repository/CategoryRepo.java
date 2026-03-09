package com.cognizant.omni.omniretail.repository;

import com.cognizant.omni.omniretail.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    boolean existsByName(String name);
    List<Category> findByParentCategoryIsNull();
}
