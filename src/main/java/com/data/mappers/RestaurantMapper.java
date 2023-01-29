package com.data.mappers;


import com.data.dtos.RestaurantDTO;
import com.data.entities.Restaurant;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDTO toRestaurantDTO(Restaurant restaurant);

    List<RestaurantDTO> toRestaurantDTOs(List<Restaurant> restaurants);

}
