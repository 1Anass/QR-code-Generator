package com.controllers;

import com.data.entities.Wifi;
import com.services.WifiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wifi")
public class WifiController {

    private final WifiService wifiService;

    @PostMapping("/addWifi")
    public ResponseEntity<HttpStatus> addWifi(@RequestParam String name, @RequestParam String city, @RequestBody Wifi wifi) {
        return wifiService.addWifi(name, city, wifi);
    }

    @GetMapping("/getWifi")
    public ResponseEntity<HttpStatus> findWifiByRestaurant(@RequestParam String name, @RequestParam String city){
        return wifiService.findWifiByRestaurant(name, city);
    }

    @PatchMapping("/updateWifi/updatePassword")
    public ResponseEntity<HttpStatus> updateWifiPassword(@RequestParam String name, @RequestParam String city, @RequestParam String oldPassword, @RequestBody String password) {
        return wifiService.updateWifiPassword(name, city, oldPassword, password);
    }

    @PatchMapping("/updateWifi/updateName")
    public ResponseEntity<HttpStatus> updateWifiName(@RequestParam String name, @RequestParam String city, @RequestBody String wifiName) {
        return wifiService.updateWifiName(name, city, wifiName);
    }

    @PatchMapping("/updateWifi/updateEncryption")
    public ResponseEntity<HttpStatus> updateWifiEncryption(@RequestParam String name, @RequestParam String city, @RequestBody String encryption) {
        return wifiService.updateWifiEncryption(name, city, encryption);
    }

    @PostMapping("/deleteWifi")
    public ResponseEntity<HttpStatus> deleteWifi(@RequestParam String name, @RequestParam String city) {
        return wifiService.deleteWifi(name, city);
    }




}
