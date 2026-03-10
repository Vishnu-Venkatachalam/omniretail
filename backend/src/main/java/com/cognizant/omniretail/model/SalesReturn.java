package com.cognizant.omniretail.model;

import com.cognizant.omniretail.model.enums.ReturnReason;
import com.cognizant.omniretail.model.enums.ReturnStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="sales_return")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReturn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="return_id",unique = true, nullable = false)
    private Long returnId;

    //added saleId as foreign key (uni-directional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="sale_id",
            //referencedColumnName = "saleId",
            nullable = false
    )
    private Sale sale;
    //added saleItemId as foreign key (uni-directional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="sale_item_id",
            //referencedColumnName = "saleItemId",
            nullable = false
    )
    private SaleItem saleitem;
    @Enumerated(EnumType.STRING)
    @Column(name="reason",nullable = false)
    private ReturnReason reason;

    @Column(name="return_date",nullable = false)
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false)
    private ReturnStatus status;
}
