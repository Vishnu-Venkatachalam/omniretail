package com.cognizant.omniretail.model;
import com.cognizant.omniretail.model.enums.AssortmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Assortment",uniqueConstraints = @UniqueConstraint(
        columnNames = {"store_id","variant_id"}
))
public class Assortment {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "assortment_id",unique = true)
    private Long assortmentId;

    //uni-directional (finding assortments by store_id)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="store_id", referencedColumnName = "storeId", nullable = false)
    private Store store;

    //uni-directional (finding assortments by variant_id)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="variant_id", referencedColumnName = "variantId", nullable = false)
    private ProductVariant productvariant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssortmentStatus status;
}