package com.productinventrymanagementapp.serviceimpl;
import com.productinventrymanagementapp.constants.ApplicationConstants;
import com.productinventrymanagementapp.model.Product;
import com.productinventrymanagementapp.model.Store;
import com.productinventrymanagementapp.response.ResponseHandler;
import com.productinventrymanagementapp.service.CsvService;
import com.productinventrymanagementapp.service.ProductService;
import com.productinventrymanagementapp.service.StoreService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CsvServiceImpl implements CsvService {

    @Autowired
    private ProductService productService;

    @Autowired
    private StoreService storeService;

    @Override
    public ResponseEntity<?> uploadStoreAndProduct(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseHandler.generateResponse(ApplicationConstants.BAD_REQUEST, HttpStatus.BAD_REQUEST, "File is empty...");
        }

        int recordCount = 0;
        Map<String, Store> allStores = new HashMap<>();
        Map<String, Product> allProducts = new HashMap<>();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                recordCount++;

                // Process Store
                Store store = getStore(csvRecord);
                if(store != null){
                    if (!allStores.containsKey(store.getStoreCode())) {
                        allStores.put(store.getStoreCode(), store);
                    } else {
                        Store existingStore = allStores.get(store.getStoreCode());
                        existingStore.setStoreName(store.getStoreName());
                        existingStore.setStoreDescription(store.getStoreDescription());
                        existingStore.setStoreOpeningDate(store.getStoreOpeningDate());
                        allStores.put(store.getStoreCode(),existingStore);
                    }
                }
                // Process Product
                Product product = getProduct(csvRecord, store != null?allStores.get(store.getStoreCode()) : null);
                if (product != null) {
                    if (!allProducts.containsKey(product.getProductEAN())) {
                        allProducts.put(product.getProductEAN(), product);
                    } else {
                        // Update existing product details
                        Product existingProduct = allProducts.get(product.getProductEAN());
                        existingProduct.setProductName(product.getProductName());
                        existingProduct.setProductDescription(product.getProductDescription());
                        existingProduct.setStoreId(store.getId());
                        existingProduct.setExtraAttributes(product.getExtraAttributes());
                        allProducts.put(product.getProductEAN(),existingProduct);
                    }
                }
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(ApplicationConstants.INTERNAL_ERROR, HttpStatus.MULTI_STATUS, e.getMessage());
        }

        List<Store> storeList = new ArrayList<>(allStores.values());
        List<Product> productList = new ArrayList<>(allProducts.values());

        storeService.saveAllStore(storeList);
        productService.saveAllProduct(productList);

        return ResponseHandler.generateResponse(ApplicationConstants.OK, HttpStatus.OK, recordCount + " (Number of records processed)");
    }

    private Product getProduct(CSVRecord csvRecord,Store store) {
        String productEAN = csvRecord.get("Product EAN");
        String productName = csvRecord.get("Product Name");
        String productDescription = csvRecord.get("Product Description");
        Product product = null;
        if(!productEAN.isEmpty()){
            Map<String, String> productAttributes = new HashMap<>();
            csvRecord.toMap().forEach((key, value) -> {
                if (!"Store Code".equalsIgnoreCase(key) && !"Store Name".equalsIgnoreCase(key) &&
                        !"Store Description".equalsIgnoreCase(key) && !"Store Opening Date".equalsIgnoreCase(key) &&
                        !"Product EAN".equalsIgnoreCase(key) && !"Product Name".equalsIgnoreCase(key) && !"Product Description".equalsIgnoreCase(key) && !value.isEmpty()) {
                    productAttributes.put(key, value);
                }
            });

            product = productService.findByProductEAN(productEAN);
            if (product == null) {
                product = new Product();
                product.setProductEAN(productEAN);
            }
            if(!productName.isEmpty())
                product.setProductName(productName);
            if(!productDescription.isEmpty())
                product.setProductDescription(productDescription);
            if(store != null)
                product.setStoreId(store.getId());
            product.setExtraAttributes(productAttributes);
        }
        return product;
    }

    private Store getStore(CSVRecord csvRecord) {
        String storeCode = csvRecord.get("Store Code");
        String storeName = csvRecord.get("Store Name");
        String storeDescription = csvRecord.get("Store Description");
        String storeOpeningDate = csvRecord.get("Store Opening Date");

        Store estore = null;
        if(!storeCode.isEmpty()){
            Store store = new Store();
            store.setStoreCode(storeCode);
            if(!storeName.isEmpty())
                store.setStoreName(storeName);
            if(!storeDescription.isEmpty())
                store.setStoreDescription(storeDescription);
            if(!storeOpeningDate.isEmpty())
                store.setStoreOpeningDate(storeOpeningDate);
            estore = storeService.findByStoreCode(storeCode);
            if (estore == null) {
                estore = storeService.saveStore(store);
            }
            else{
                estore.setStoreName(store.getStoreName());
                estore.setStoreDescription(store.getStoreDescription());
                estore.setStoreOpeningDate(store.getStoreOpeningDate());
            }
        }
        return estore;
    }
}
