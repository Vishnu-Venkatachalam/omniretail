package com.cognizant.omniretail.repository;

import com.cognizant.omniretail.model.StoreInventory;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreInventoryRepository extends JpaRepository<StoreInventory,Long> {


    //1. get all inventories of a specific productVariant by its variantId
    @Query("select i from StoreInventory i WHERE i.variant.variantId =:variantId")
    List<StoreInventory> findAllVariantInventories(Long variantId);

    //2. get all inventories of a specific store by its storeId
    @Query("select i from StoreInventory i WHERE i.store.storeId =:storeId")
    List<StoreInventory> findAllStoreInventories(Long storeId);

    //3. get all inventories of a specific store and a specific variant by their ids
    @Query("select i from StoreInventory i WHERE" +
            "i.store.storeId =:storeId AND i.variant.variantId =:variantId")
    List<StoreInventory> findAllStoreAndVariantInventories(Long storeId, Long variantId);

    //4. find the inventory with least stock for a variant under a store
    StoreInventory findTopByStore_StoreIdAndVariant_VariantIdOrderByOnHandQuantityAsc(
            Long storeId, Long variantId);

    //5. find the inventory with most stock for a variant under a store
    StoreInventory findTopByStore_StoreIdAndVariant_VariantIdOrderByOnHandQuantityDesc(
            Long storeId, Long variantId);


}
