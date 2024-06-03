package com.productinventrymanagementapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productinventrymanagementapp.constants.ApplicationConstants;
import com.productinventrymanagementapp.dto.StoreResponseDto;
import com.productinventrymanagementapp.model.Store;
import com.productinventrymanagementapp.response.ResponseHandler;
import com.productinventrymanagementapp.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreController {
    @Autowired
    private StoreService storeService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/stores/{code}")
    public ResponseEntity<?> getStore(@PathVariable("code") String code){
        Store store = storeService.findByStoreCode(code);
        if(store == null)
            return ResponseHandler.generateResponse(ApplicationConstants.NOT_FOUND, HttpStatus.NOT_FOUND,"store with the specified code does not exist.");
        StoreResponseDto st = objectMapper.convertValue(store,StoreResponseDto.class);
        return new ResponseEntity<>(st,HttpStatus.OK);
    }
}
