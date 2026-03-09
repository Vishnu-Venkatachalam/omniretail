package com.cognizant.omniretail.model;
import com.cognizant.omniretail.model.enums.TransferStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name="store_transfer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id",nullable = false)
    private Long transferId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "fromStore_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_TransferRequest_fromStore")
    )
    @ToString.Exclude
    private Store fromStore;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "toStore_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_TransferRequest_toStore")
    )
    @ToString.Exclude
    private Store toStore;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "variant_id",
            referencedColumnName = "variantId",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_TransferRequest_ProductVariant")
    )
    @ToString.Exclude
    private ProductVariant variant;

    @Min(1)
    @Column(name = "quantity", nullable = false)
    private Long quantity;



    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private TransferStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "requestedByUserID",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_TransferRequest_User")
    )
    @ToString.Exclude
    private User requestedBy;

    @CreationTimestamp // auto-set when persisted (Hibernate)
    @Column(name = "requestedAt", nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    @CreationTimestamp // auto-set when persisted (Hibernate)
    @Column(name = "decisionAt")
    private LocalDateTime decisionAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "decidedByUserID",
            foreignKey = @ForeignKey(name = "FK_DecidedBy_User")
    )
    @ToString.Exclude
    private User decidedBy;
}