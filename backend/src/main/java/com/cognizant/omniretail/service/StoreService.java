package com.cognizant.omniretail.service;

import com.cognizant.omniretail.model.Store;
import com.cognizant.omniretail.model.StoreInventory;
import com.cognizant.omniretail.repository.StoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    StoreRepo storeRepo;

    //get all inventories of a store
    public List<StoreInventory> getStoreInventories(Long storeId){
        return storeRepo.findAllInventoriesById(storeId);
    }

    //get all the stores in the table
    public List<Store> getAllStores(){
        return storeRepo.findAll();
    }

    //add a new store
    public Store addStore(Store store){
        return storeRepo.save(store);
    }

    //edit or update the details of a store
    public Store updateStore(Store store, Long storeId){
        return storeRepo.save(store);
    }
}

