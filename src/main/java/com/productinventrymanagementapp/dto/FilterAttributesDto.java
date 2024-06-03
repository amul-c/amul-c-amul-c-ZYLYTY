package com.productinventrymanagementapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class FilterAttributesDto {
    private Map<String, String[]> filterAttributes;
}
