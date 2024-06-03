package com.productinventrymanagementapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface CsvService {
    ResponseEntity<?> uploadStoreAndProduct(MultipartFile file);
}
