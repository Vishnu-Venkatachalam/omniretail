package com.cognizant.omniretail.repository;

import com.cognizant.omniretail.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PriceRepo extends JpaRepository<Price, Long> {

    @Query("""
    SELECT p FROM Price p
    WHERE p.productVariant.variantId = :variantId
      AND p.store.storeId = :storeId
      AND p.effectiveFrom <= :effectiveTo
      AND p.effectiveTo >= :effectiveFrom
""")
    List<Price> findOverlappingPrices(
            @Param("variantId") Long variantId,
            @Param("storeId") Long storeId,
            @Param("effectiveFrom") LocalDate effectiveFrom,
            @Param("effectiveTo") LocalDate effectiveTo
    );

    @Query("""
    SELECT p FROM Price p
    WHERE p.productVariant.variantId = :variantId
    ORDER BY p.effectiveFrom DESC, p.effectiveTo DESC
""")
    List<Price> findPricesByVariantSorted(@Param("variantId") Long variantId);

}
