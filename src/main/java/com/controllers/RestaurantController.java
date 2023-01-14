package com.controllers;

import com.data.entities.Restaurant;
import com.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/addRestaurant")
    public ResponseEntity<HttpStatus> addRestaurant(@RequestBody Restaurant restaurant){
        return restaurantService.addRestaurant(restaurant, "myoussef");
    }

    @PatchMapping("/updateRestaurant")
    public ResponseEntity<HttpStatus> updateRestaurant(@RequestBody Restaurant restaurant){
        return restaurantService.updateRestaurant(restaurant);
    }

}
