package com.cognizant.omniretail.service;

import com.cognizant.omniretail.model.Store;
import com.cognizant.omniretail.model.StoreInventory;
import com.cognizant.omniretail.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    @Autowired
    StoreRepository storeRepo;

    //1. get all inventories of a store
    public List<StoreInventory> getStoreInventories(Long storeId){
        return storeRepo.findAllInventoriesById(storeId);
    }

    //2. get all the stores in the table
    public List<Store> getAllStores(){
        return storeRepo.findAll();
    }

    //3. add a new store
    public Store addStore(Store store){
        return storeRepo.save(store);
    }
//
//    //4. edit or update the details of a store
//    public Store updateStore(Store store, Long storeId){
//        return storeRepo.save(store);
//    }
}
