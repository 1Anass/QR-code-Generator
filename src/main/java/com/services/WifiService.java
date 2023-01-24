package com.services;

import com.data.entities.Restaurant;
import com.data.entities.Wifi;
import com.data.repositories.WifiRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WifiService {


    private final WifiRepository wifiRepository;
    private final RestaurantService restaurantService;

    @Value("${spring.qrCodesDirectory}")
    private String qrCodesDirectory;

    public ResponseEntity<HttpStatus> addWifi(String restaurantName, String city, Wifi wifiDto) {

        Restaurant restaurant = restaurantService.findRestaurant(restaurantName, city);
        if (restaurant == null) {
            log.error("The corresponding restaurant does not exist");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Wifi wifi = Wifi.builder()
                .wifiName(wifiDto.getWifiName())
                .password(wifiDto.getPassword())
                .encryption(wifiDto.getEncryption())
                .restaurant(restaurant)
                .build();

        String qrcodePath = qrCodesDirectory + "/wifi/" + "/" + restaurant.getName() + "_" + restaurant.getCity() + ".pdf";
        wifi.setQRCodePath(qrcodePath);

        wifiRepository.save(wifi);
        restaurantService.save(restaurant);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public Wifi findWifiByRestaurant(String name, String city){

        Restaurant restaurant = restaurantService.findRestaurant(name, city);
        if (restaurant == null) {
            return null;
        }
        return restaurant.getWifi();
    }

    public Wifi findById(Long id){
        return wifiRepository.findByWifiId(id).orElse(null);
    }

    public ResponseEntity<HttpStatus> updateWifiPassword(String id,String password){
        Wifi wifi = findById(Long.valueOf(id));
        wifi.setPassword(password);
        wifiRepository.save(wifi);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<HttpStatus> updateWifiName(String id,String wifiName){
        Wifi wifi = findById(Long.valueOf(id));
        wifi.setWifiName(wifiName);
        wifiRepository.save(wifi);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> updateWifiEncryption(String id,String encryption){
        Wifi wifi = findById(Long.valueOf(id));
        wifi.setWifiName(encryption);
        wifi.setEncryption(encryption);
        wifiRepository.save(wifi);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteWifi(String wifiId){
        Wifi wifi = findById(Long.valueOf(wifiId));
        wifiRepository.delete(wifi);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
