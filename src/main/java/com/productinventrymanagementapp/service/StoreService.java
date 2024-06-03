package com.productinventrymanagementapp.service;

import com.productinventrymanagementapp.model.Store;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StoreService {
    ResponseEntity<?> saveAllStore(List<Store> stores);
    Store saveStore(Store store);
    Store findByStoreCode(String storeCode);
}
