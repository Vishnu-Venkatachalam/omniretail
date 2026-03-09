package com.cognizant.omniretail.service;

import com.cognizant.omniretail.model.StoreInventory;
import com.cognizant.omniretail.repository.StoreInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreInventoryService {

    @Autowired
    StoreInventoryRepository inventoryRepo;

    //1. add an inventory
    public StoreInventory addInventory(StoreInventory inventory){
        return inventoryRepo.save(inventory);
    }

    //2. get all the inventories in the table (not based on storeId or variantId)
    public List<StoreInventory> getAllInventories(){
        return inventoryRepo.findAll();
    }

    //3. get all the inventories of a productVariant
    public List<StoreInventory> getVariantInventory(Long variantId){
        return inventoryRepo.findAllVariantInventories(variantId);
    }
    //4. get all the inventories of a store
    public List<StoreInventory> getStoreInventory(Long storeId){
        return inventoryRepo.findAllStoreInventories(storeId);
    }

    //5. get all the inventories of a specific store and a variant
    public List<StoreInventory> getStoreAndVariantInventory(Long storeId, Long variantId){
        return inventoryRepo.findAllStoreAndVariantInventories(storeId, variantId);
    }

    //6. restock the least stock inventory for a specific variant under a store (replenishment)
    public StoreInventory addStockInventory(Long storeId, Long variantId, Integer quantity) throws Exception {
        StoreInventory minStock = inventoryRepo.findTopByStore_StoreIdAndVariant_VariantIdOrderByOnHandQuantityAsc(storeId,variantId);
        if(minStock == null) {
            throw new Exception("Requested inventory not found");
        }
        minStock.setOnHandQuantity(minStock.getOnHandQuantity() + quantity);
        return inventoryRepo.save(minStock);

    }

    //
    public StoreInventory subtractStockInventory(Long storeId, Long variantId){
        return inventoryRepo.findTopByStore_StoreIdAndVariant_VariantIdOrderByOnHandQuantityAsc(storeId,variantId);
    }


}
