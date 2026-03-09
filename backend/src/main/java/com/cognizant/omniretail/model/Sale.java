package com.cognizant.omniretail.model;

import com.cognizant.omniretail.model.enums.SaleChannel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Sale",
        indexes = {
                @Index(name="sale_store_id",columnList="store_id"),
                @Index(name="sale_sale_date",columnList = "sale_date"),
                @Index(name="sale_channel",columnList = "channel")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sale_id", unique = true)
    private Long saleId;

    //added storeId as foreign key (uni-directional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="store_id",
            referencedColumnName = "storeId",
            nullable = false
    )
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(name="channel",nullable = false)
    private SaleChannel channel;
    @Column(name="sale_date")
    private LocalDate saleDate;
    @Column(name="total_amount", nullable = false,precision=12,scale=2)
    private BigDecimal totalAmount;

    //bi-directional
    @OneToMany(mappedBy = "sale",cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference
    private List<SaleItem> saleItems=new ArrayList<>();
}
