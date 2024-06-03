package com.productinventrymanagementapp.serviceimpl;

import com.productinventrymanagementapp.constants.ApplicationConstants;
import com.productinventrymanagementapp.model.Store;
import com.productinventrymanagementapp.repository.StoreRepository;
import com.productinventrymanagementapp.response.ResponseHandler;
import com.productinventrymanagementapp.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;
    @Override
    public ResponseEntity<?> saveAllStore(List<Store> stores) {
        storeRepository.saveAll(stores);
        return ResponseHandler.generateResponse(ApplicationConstants.OK, HttpStatus.OK,"Added");
    }

    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store findByStoreCode(String storeCode) {
        return storeRepository.findByStoreCode(storeCode);
    }
}
