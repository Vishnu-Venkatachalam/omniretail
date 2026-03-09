package com.cognizant.omniretail.model;

import com.cognizant.omniretail.model.enums.StoreStatus;
import com.cognizant.omniretail.model.enums.StoreType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "Store"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id",unique = true)
    private Long storeId;

    @Column(name = "store_name", nullable = false)
    private String name;

    @Column(name = "store_location", nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_type", nullable = false)
    private StoreType storeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_status", nullable = false)
    private StoreStatus status;


    //Bi-directional mapping
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY) //receiver
    private List<StoreInventory> storeInventoryList = new ArrayList<>();
}
