package com.productinventrymanagementapp.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreResponseDto {
    private String storeCode;
    private String storeName;
    private String storeDescription;
    private String storeOpeningDate;
}
