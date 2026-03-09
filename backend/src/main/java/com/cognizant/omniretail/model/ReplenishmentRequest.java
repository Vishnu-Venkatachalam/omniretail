package com.cognizant.omniretail.model;

import com.cognizant.omniretail.model.enums.ReplenishRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "replenishment_request",
        indexes = {

                @Index(name = "idx_repl_store", columnList = "storeID"),
                @Index(name = "idx_repl_variant", columnList = "VariantID"),
                @Index(name = "idx_repl_status", columnList = "Status")

        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplenishmentRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReplenishmentID", unique = true, nullable = false)
    private Long replenishmentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "store_id",
            referencedColumnName = "storeId",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_ReplenishmentRequest_Store")
    )
    @ToString.Exclude
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "variant_id",
            referencedColumnName = "variantId",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_ReplenishmentRequest_ProductVariant")
    )
    @ToString.Exclude
    private ProductVariant variant;

    @Min(1)
    @Column(name = "RequestedQty", nullable = false)
    private long requestedQty;


    @Enumerated(EnumType.STRING)
    @Column(name = "Status", length = 20, nullable = false)
    private ReplenishRequestStatus status;

    @CreationTimestamp // auto-set when persisted (Hibernate)
    @Column(name = "RequestedAt", nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "RequestedByUserID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_ReplenishmentRequest_User")
    )
    @ToString.Exclude
    private User requestedBy;

}
