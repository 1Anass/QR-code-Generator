package com.data.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RestaurantDTO {

    private String name;
    private String city;
    private String address;
    private String serviceType;
    private String numberOfTables;
    private String maxCapacity;

}
