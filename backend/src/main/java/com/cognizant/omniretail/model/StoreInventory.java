package com.cognizant.omniretail.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "StoreInventory",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "store_id", "variant_id"
                })
        }

)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreInventory {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "inventory_id", nullable = false)
    private Long inventoryId;


    @Column(name = "onHand_quantity", nullable = false)
    @Min(0)
    private Integer onHandQuantity;

    @Column(name = "reserved_quantity", nullable = false)
    @Min(0)
    private Integer reservedQuantity;

    //foreign-key -> store_id from 'store' table
    @ManyToOne(
            fetch = FetchType.LAZY,//owner (the one who has FK)
            optional = false // a store's inventory cannot exist without a store
    )
    @JoinColumn(
            name = "store_id",
            //referencedColumnName = "store_id",
            nullable = false
    )
    private Store store;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false // a variant's inventory should not be there without a variant mapped to it
    )
    @JoinColumn(
            name = "variant_id",
            //referencedColumnName = "variantId",
            nullable = false
    )
    private ProductVariant variant;





}
