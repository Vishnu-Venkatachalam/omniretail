package com.cognizant.omniretail.controller;

import com.cognizant.omniretail.model.Store;
import com.cognizant.omniretail.model.StoreInventory;
import com.cognizant.omniretail.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    StoreService storeService;

    @GetMapping("/{storeId}/Inventory") //get all inventories of a store
    public List<StoreInventory> getAllInventories(@PathVariable Long storeId){
        return storeService.getStoreInventories(storeId);
    }

    @GetMapping("/all") //get all the stores
    public List<Store> getAllStores(){
        return storeService.getAllStores();
    }

    @PostMapping("/add") //add a new store
    public Store addStore(@RequestBody Store store){
        return storeService.addStore(store);
    }

    @PutMapping("/update/{storeId}") //edit or update the details of a store
    public Store updateStore(@RequestBody Store store, @PathVariable Long storeId){
        return storeService.updateStore(store,storeId);
    }
}
