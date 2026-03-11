package com.cognizant.omniretail.controller;

import com.cognizant.omniretail.model.Store;
import com.cognizant.omniretail.model.StoreInventory;
import com.cognizant.omniretail.service.StoreInventoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@SecurityRequirement(name = "bearerAuth")
public class StoreInventoryController {

    @Autowired
    StoreInventoryService inventoryService;

    //1. get all the inventories in the table
    @GetMapping("/all")
    public List<StoreInventory> getAllInventories(){
        return inventoryService.getAllInventories();
    }

    //2. get all inventories of a product
    @GetMapping("/variant/{variantId}")
    public List<StoreInventory> getAllVariantInventories(@PathVariable Long variantId){
        return inventoryService.getVariantInventory(variantId);
    }

    //3. get all inventories of a store
    @GetMapping("/store/{storeId}")
    public List<StoreInventory> getAllStoreInventories(@PathVariable Long storeId){
        return inventoryService.getStoreInventory(storeId);
    }

    //4. get all inventories of a variant sold in a store
    @GetMapping("/store/{storeId}/variant/{variantId}")
    public List<StoreInventory> getAllStoreAndVariantInventories(@PathVariable Long storeId, @PathVariable Long variantId){
        return inventoryService.getStoreAndVariantInventory(storeId, variantId);
    }

    //5. Replenish stock in the least stock inventory of a variant under a store
    @PatchMapping("/store/{storeId}/variant/{variantId}")
    public StoreInventory replenishStock(@PathVariable Long storeId,@PathVariable Long variantId, @RequestParam Integer quantity) throws Exception {
        return inventoryService.addStockInventory(storeId,variantId,quantity);
    }

    //6. adding an inventory
    @PostMapping("/add")
    public StoreInventory addInventory(@RequestBody StoreInventory storeInventory){
        return inventoryService.addInventory(storeInventory);
    }

    //7. Reserving stock
    @PatchMapping("reserve/{inventoryId}")
    public StoreInventory reserveStock(@PathVariable Long inventoryId, @RequestParam Integer reserveQuantity) throws Exception {
        return inventoryService.reserveStock(inventoryId, reserveQuantity);
    }
}
