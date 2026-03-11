package com.cognizant.omniretail.repository;

import com.cognizant.omniretail.model.Assortment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssortmentRepo extends JpaRepository<Assortment, Long> {

    List<Assortment> findByStore_StoreId(Long storeId);

    Optional<Assortment> findByStore_StoreIdAndProductvariant_VariantId(Long storeId, Long variantId);

}
