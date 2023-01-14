package com.controllers;

import com.data.entities.Restaurant;
import com.services.MenuService;
import com.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/addMenu")
    public ResponseEntity<HttpStatus> addRestaurant(@RequestParam String menuName, @RequestParam String name, @RequestParam String city, @RequestBody byte[] file) {
        return menuService.addMenu(menuName, name, city, file);
    }

    @PatchMapping("/updateMenu")
    public ResponseEntity<HttpStatus> updateRestaurant(@RequestParam String name, @RequestParam String city, @RequestBody byte[] file) {
        return menuService.updateMenu(name, city, file);
    }

}
