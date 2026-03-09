package com.cognizant.omni.omniretail.repository;

import com.cognizant.omni.omniretail.model.Store;
import com.cognizant.omni.omniretail.model.StoreInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepo extends JpaRepository<Store, Long>{
    //Get all the inventories of a store using its storeId
    @Query("select s.storeInventoryList from Store s WHERE s.storeId = ?1")
    List<StoreInventory> findAllInventoriesById(Long storeId);
}
