package com.data.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;

@Data
@RequiredArgsConstructor
public class RestaurantDto {

    private final String name;
    private final String city;

}
