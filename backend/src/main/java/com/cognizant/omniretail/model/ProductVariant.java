package com.cognizant.omniretail.model;
import com.cognizant.omniretail.model.enums.VariantStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ProductVariant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id", unique = true)
    private Long variantId;

    //bi-directional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id",
//            referencedColumnName = "productId",
            nullable = false)
    private Product product;

    @Column(nullable = false,unique = true)
    private String sku;

    @Column(name="size",nullable = false)
    private String size;

    @Column(nullable = false)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VariantStatus status;
}