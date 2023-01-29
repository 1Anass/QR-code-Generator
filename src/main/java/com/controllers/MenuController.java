package com.controllers;

import com.data.entities.Menu;
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

    @GetMapping("/findMenu")
    public Menu findMenu(@RequestParam String name, @RequestParam String city){
        return menuService.findMenu(name, city);
    }

    @GetMapping("/findMenuFile")
    public byte[] findMenuFile(@RequestParam String name, @RequestParam String city){
        return menuService.findMenuFile(name, city);
    }

    @GetMapping("/findMenuQRCde")
    public byte[] findMenuQRCode(@RequestParam String name, @RequestParam String city){
        return menuService.findMenuQRCode(name, city);
    }

    @PostMapping("/deleteMenu")
    public ResponseEntity<HttpStatus> deleteMenu(@RequestParam String name, @RequestParam String city){
        return menuService.deleteMenu(name, city);
    }

}
