package com.cognizant.omniretail.model;

import com.cognizant.omniretail.model.embeddable.RetailMetrics;
import com.cognizant.omniretail.model.enums.RetailScope;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name="retail_report")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetailReport {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="report_id",unique = true, nullable = false,updatable = false)
    private Long reportId;
    @Enumerated(EnumType.STRING)
    @Column(name="scope",nullable = false,length=100)
    private RetailScope scope;

    @Embedded
    private RetailMetrics metrics;


    //this sets the time when the row is inserted
    @CreationTimestamp
    @Column(name="generated_date",nullable = false)
    private LocalDateTime genDate;
}
