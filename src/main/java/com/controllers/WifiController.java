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
    public Wifi findWifiByRestaurant(@RequestParam String name, @RequestParam String city){
        return wifiService.findWifiByRestaurant(name, city);
    }

    @PatchMapping("/updateWifi/updatePassword")
    public ResponseEntity<HttpStatus> updateWifiPassword(@RequestParam String wifiId, @RequestBody String password) {
        return wifiService.updateWifiPassword(wifiId, password);
    }

    @PatchMapping("/updateWifi/updateName")
    public ResponseEntity<HttpStatus> updateWifiName(@RequestParam String wifiId, @RequestBody String wifiName) {
        return wifiService.updateWifiName(wifiId, wifiName);
    }

    @PatchMapping("/updateWifi/updateEncryption")
    public ResponseEntity<HttpStatus> updateWifiEncryption(@RequestParam String wifiId, @RequestBody String encryption) {
        return wifiService.updateWifiEncryption(wifiId, encryption);
    }

    @PostMapping("/deleteWifi")
    public ResponseEntity<HttpStatus> addWifi(@RequestParam String wifiId) {
        return wifiService.deleteWifi(wifiId);
    }




}
