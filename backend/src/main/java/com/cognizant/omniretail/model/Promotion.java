package com.cognizant.omniretail.model;
import com.cognizant.omniretail.model.enums.PromotionStatus;
import com.cognizant.omniretail.model.enums.PromotionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Promotion")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="promotion_id")
    private Long promotionId;

    @Column(nullable = false,unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name="discount_type",nullable = false)
    private PromotionType discountType;
    @Column(name="discount_value", nullable = false)
    private Double discountValue;

    @Column(name="start_date", nullable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PromotionStatus status;

}

