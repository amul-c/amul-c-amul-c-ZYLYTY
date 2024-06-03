package com.productinventrymanagementapp.service;

import com.productinventrymanagementapp.dto.ProductResponseDto;
import com.productinventrymanagementapp.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<?> saveAllProduct(List<Product> products);
    Product findByProductEAN(String productEan);

    List<ProductResponseDto> productFilters(Map<String, String[]> filters);
    List<ProductResponseDto> getProducts(Pageable page);
}
