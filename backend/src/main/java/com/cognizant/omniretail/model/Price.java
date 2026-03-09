package com.cognizant.omniretail.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Price", uniqueConstraints = @UniqueConstraint(
        name = "uk_variant_store_effective",
        columnNames = {"variant_id","store_id","effective_from"}
))
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id", unique = true)
    private Long priceId;

    //uni-directional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="variant_id", referencedColumnName = "variantId", nullable = false)
    private ProductVariant productVariant;

    //uni-directional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="store_id",referencedColumnName = "storeId",nullable = false)
    private Store store;

    @Column(name = "MRP", nullable = false, precision = 12, scale = 2)
    private BigDecimal mrp;

    @Column(name = "selling_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal sellingPrice;

    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to")
    private LocalDate effectiveTo;
}