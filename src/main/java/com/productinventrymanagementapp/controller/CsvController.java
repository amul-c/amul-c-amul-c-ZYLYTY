package com.productinventrymanagementapp.controller;

import com.productinventrymanagementapp.response.ResponseHandler;
import com.productinventrymanagementapp.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/csv")
public class CsvController {

    @Autowired
    private CsvService csvService;

    @PostMapping("")
    public ResponseEntity<?> uploadCSV(@RequestParam("file") MultipartFile file) {
        try{
            return csvService.uploadStoreAndProduct(file);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null);
        }
    }
}
