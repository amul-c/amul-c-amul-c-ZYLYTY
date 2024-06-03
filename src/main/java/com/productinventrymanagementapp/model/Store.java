package com.productinventrymanagementapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "stores")
@Getter
@Setter
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String storeCode;

    @Column(name = "name")
    private String storeName;
    @Column(name = "description")
    private String storeDescription;
    @Column(name = "storeOpeningDate")
    private String storeOpeningDate;
}
