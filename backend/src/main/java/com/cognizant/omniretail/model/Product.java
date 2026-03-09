package com.cognizant.omniretail.model;

import com.cognizant.omniretail.model.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true)
    private Long productId;

    @Column(name="product_name",nullable = false)
    private String name;

    @Column(name="product_brand", nullable = false)
    private String brand;

    //uni-directional
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id",referencedColumnName = "categoryId", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus status;

    //bi-directional
    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    private List<ProductVariant> variants = new ArrayList<>();

}