package com.productinventrymanagementapp.serviceimpl;

import com.productinventrymanagementapp.constants.ApplicationConstants;
import com.productinventrymanagementapp.dto.ProductResponseDto;
import com.productinventrymanagementapp.model.Product;
import com.productinventrymanagementapp.repository.ProductRepository;
import com.productinventrymanagementapp.response.ResponseHandler;
import com.productinventrymanagementapp.service.ProductService;
import com.productinventrymanagementapp.util.ProductResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public ResponseEntity<?> saveAllProduct(List<Product> products) {
        productRepository.saveAll(products);
        return ResponseHandler.generateResponse(ApplicationConstants.OK, HttpStatus.OK,"Added");
    }

    @Override
    public Product findByProductEAN(String productEan) {
        return productRepository.findByProductEAN(productEan);
    }
    @Override
    public List<ProductResponseDto> productFilters(Map<String, String[]> filterAttributes) {
        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Product> result = products.stream()
                .filter(product -> product.getExtraAttributes() != null && !product.getExtraAttributes().isEmpty())
                .filter(product -> filterAttributes.entrySet().stream()
                        .anyMatch(entry -> {
                            String key = entry.getKey();
                            String[] values = entry.getValue();
                            String attributeValue = product.getExtraAttributes().get(key);

                            return attributeValue != null &&
                                    Arrays.stream(values).anyMatch(value -> value.equalsIgnoreCase(attributeValue));
                        }))
                .collect(Collectors.toSet());

        return result.stream()
                .sorted(Comparator.comparing(Product::getProductEAN))
                .map(ProductResponseHandler::mapProductResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getProducts(Pageable page) {
        Page<Product> products = productRepository.findAll(page);
        if(products != null && !products.isEmpty()){
            return products.stream()
                    .map(ProductResponseHandler::mapProductResponseDto)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
