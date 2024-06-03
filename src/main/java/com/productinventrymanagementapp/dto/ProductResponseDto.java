package com.productinventrymanagementapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.productinventrymanagementapp.util.HashMapConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ProductResponseDto {
    @JsonProperty("ean")
    private String productEAN;
    @JsonProperty("name")
    private String productName;
    @JsonProperty("description")
    private String productDescription;
    @JsonProperty("extraAttributes")
    private Map<String, String> extraAttributes;
}
