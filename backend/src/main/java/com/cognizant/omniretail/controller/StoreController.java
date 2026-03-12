package com.cognizant.omniretail.controller;

import com.cognizant.omniretail.model.Store;
import com.cognizant.omniretail.model.StoreInventory;
import com.cognizant.omniretail.service.StoreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/store")
public class StoreController {

    @Autowired
    StoreService storeService;

    //1. get all inventories of a store
    @GetMapping("/{storeId}/Inventory")
    public List<StoreInventory> getAllInventories(@PathVariable Long storeId){
        return storeService.getStoreInventories(storeId);
    }

    //2. get all the stores
    @GetMapping("/all")
    public List<Store> getAllStores(){
        return storeService.getAllStores();
    }

    //3. add a new store
    //@SecurityRequirement(name = "bearerAuth")
    @PostMapping("/add")
    //@PreAuthorize("hasRole(ADMIN)")
    public Store addStore(@RequestBody Store store){
        return storeService.addStore(store);
    }

//    @PutMapping("/update/{storeId}") //4. edit or update the details of a store
//    public Store updateStore(@RequestBody Store store, @PathVariable Long storeId){
//        return storeService.updateStore(store,storeId);
//    }



}
