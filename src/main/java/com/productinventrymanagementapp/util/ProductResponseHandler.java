package com.productinventrymanagementapp.util;

import com.productinventrymanagementapp.dto.ProductResponseDto;
import com.productinventrymanagementapp.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductResponseHandler {
    public static ProductResponseDto mapProductResponseDto(Product product){
        ProductResponseDto res = new ProductResponseDto();
        res.setProductEAN(product.getProductEAN());
        res.setProductName(product.getProductName());
        res.setProductDescription(product.getProductDescription());
        res.setExtraAttributes(product.getExtraAttributes());
        return res;
    }
}
