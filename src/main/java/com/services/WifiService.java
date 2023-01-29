package com.services;

import com.data.dtos.WifiDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
                .creationDate(LocalDateTime.now())
                .build();

        String qrcodePath = qrCodesDirectory + "/wifi/" + "/" + restaurant.getName() + "_" + restaurant.getCity() + ".pdf";
        wifi.setQRCodePath(qrcodePath);

        wifiRepository.save(wifi);
        restaurant.setWifi(wifi);
        restaurantService.save(restaurant);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<HttpStatus> findWifiByRestaurant(String name, String city){

        Restaurant restaurant = restaurantService.findRestaurant(name, city);
        if (restaurant == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        Wifi wifi = restaurant.getWifi();
        WifiDTO wifiDTO = new WifiDTO();
        wifiDTO.setWifiName(wifi.getWifiName());
        wifiDTO.setEncryption(wifi.getEncryption());
        wifiDTO.setCreationDate(wifi.getCreationDate());
        wifiDTO.setModificationDate(wifiDTO.getModificationDate());
        return new ResponseEntity(wifiDTO, HttpStatus.FOUND);
    }

    public Wifi findById(Long id){
        return wifiRepository.findByWifiId(id).orElse(null);
    }

    public ResponseEntity<HttpStatus> updateWifiPassword(String name, String city,String oldPassword,String password){
        Restaurant restaurant = restaurantService.findRestaurant(name, city);
        if (restaurant == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        Wifi wifi = restaurant.getWifi();
        if(!oldPassword.equals(wifi.getPassword())){
            return new ResponseEntity(null, HttpStatus.NOT_MODIFIED);
        }
        wifi.setPassword(password);
        wifi.setModificationDate(LocalDateTime.now());
        wifiRepository.save(wifi);
        WifiDTO wifiDTO = new WifiDTO();
        wifiDTO.setWifiName(wifi.getWifiName());
        wifiDTO.setEncryption(wifi.getEncryption());
        wifiDTO.setCreationDate(wifi.getCreationDate());
        wifiDTO.setModificationDate(wifiDTO.getModificationDate());
        return new ResponseEntity(wifiDTO, HttpStatus.OK);

    }

    public ResponseEntity<HttpStatus> updateWifiName(String name, String city,String wifiName){
        Restaurant restaurant = restaurantService.findRestaurant(name, city);
        if (restaurant == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        Wifi wifi = restaurant.getWifi();
        wifi.setWifiName(wifiName);
        wifi.setModificationDate(LocalDateTime.now());
        wifiRepository.save(wifi);
        WifiDTO wifiDTO = new WifiDTO();
        wifiDTO.setWifiName(wifi.getWifiName());
        wifiDTO.setEncryption(wifi.getEncryption());
        wifiDTO.setCreationDate(wifi.getCreationDate());
        wifiDTO.setModificationDate(wifi.getModificationDate());
        return new ResponseEntity(wifiDTO, HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> updateWifiEncryption(String name, String city,String encryption){
        Restaurant restaurant = restaurantService.findRestaurant(name, city);
        if (restaurant == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        Wifi wifi = restaurant.getWifi();
        wifi.setEncryption(encryption);
        wifi.setModificationDate(LocalDateTime.now());
        wifiRepository.save(wifi);
        WifiDTO wifiDTO = new WifiDTO();
        wifiDTO.setWifiName(wifi.getWifiName());
        wifiDTO.setEncryption(wifi.getEncryption());
        wifiDTO.setCreationDate(wifi.getCreationDate());
        wifiDTO.setModificationDate(wifiDTO.getModificationDate());
        return new ResponseEntity(wifiDTO, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<HttpStatus> deleteWifi(String name, String city){
        Restaurant restaurant = restaurantService.findRestaurant(name, city);
        if (restaurant == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }

        Wifi wifi = restaurant.getWifi();
        restaurant.setWifi(null);
        restaurantService.save(restaurant);
        wifiRepository.delete(wifi);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
