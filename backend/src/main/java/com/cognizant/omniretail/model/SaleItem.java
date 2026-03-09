package com.cognizant.omniretail.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name="sale_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sale_item_id",nullable = false)
    private Long saleItemId;
    //    added saleId as foreign key (bi-directional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(
            name="sale_id",
            referencedColumnName = "saleId",
            nullable = false
    )
    private Sale sale;
    //    added variantId as foreign key (uni-directional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="variant_id",
            referencedColumnName = "variantId",
            nullable=false
    )
    private ProductVariant variant;
    @Column(name="quantity",nullable = false)
    private Integer quantity;
    @Column(name="unit_price",nullable = false,precision = 12,scale=2)
    private BigDecimal unitPrice;
}
