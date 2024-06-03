package com.productinventrymanagementapp.controller;
import com.productinventrymanagementapp.constants.ApplicationConstants;
import com.productinventrymanagementapp.dto.FilterAttributesDto;
import com.productinventrymanagementapp.dto.ProductResponseDto;
import com.productinventrymanagementapp.model.Product;
import com.productinventrymanagementapp.response.ResponseHandler;
import com.productinventrymanagementapp.service.ProductService;
import com.productinventrymanagementapp.util.ProductResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products/{productEan}")
    public ResponseEntity<?> getProduct(@PathVariable("productEan") String productEan){
        Product product = productService.findByProductEAN(productEan);
        if(product == null){
            return ResponseHandler.generateResponse("EAN does not exist.",HttpStatus.NOT_FOUND,null);
        }
        ProductResponseDto res = ProductResponseHandler.mapProductResponseDto(product);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PostMapping("/filter/products")
    public ResponseEntity<?> filterProducts(@RequestBody FilterAttributesDto filterAttributes) {
        List<ProductResponseDto> products = productService.productFilters(filterAttributes.getFilterAttributes());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProducts(@RequestParam("page_size") Integer pageSize,@RequestParam("page") Integer page) {
        if (page < 0 || pageSize <= 0) {
            return ResponseHandler.generateResponse(ApplicationConstants.BAD_REQUEST,HttpStatus.BAD_REQUEST, "page or page_size parameters are invalid.");
        }
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("productEAN").ascending());
        List<ProductResponseDto> products = productService.getProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
