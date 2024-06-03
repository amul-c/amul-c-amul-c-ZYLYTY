package com.productinventrymanagementapp.model;

import com.productinventrymanagementapp.util.HashMapConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_Id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String productEAN;

    @Column(name = "product_name")
    private String productName;
    @Column(name="product_description")
    private String productDescription;


    @Column(name = "extra_attributes", columnDefinition = "TEXT")
    @Convert(converter = HashMapConverter.class)
    private Map<String, String> extraAttributes;

    @Column(name = "store_id")
    private Long storeId;
}
