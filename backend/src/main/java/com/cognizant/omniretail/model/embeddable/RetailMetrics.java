package com.cognizant.omniretail.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetailMetrics {
    private long totalSales;
    @Column(precision = 19, scale = 2)
    private BigDecimal totalSaleAmount;
    private long unitsSold;
    @Column(precision = 9, scale = 2)
    private BigDecimal markdownRate;
    @Column(precision = 9, scale = 2)
    private BigDecimal inventoryTurnover;
    @Column(precision = 9, scale = 2)
    private BigDecimal sellThrough;

}
