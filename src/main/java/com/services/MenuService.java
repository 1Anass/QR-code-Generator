package com.services;


import com.data.entities.Menu;
import com.data.entities.Restaurant;
import com.data.repositories.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantService restaurantService;
    @Value("${spring.filesDirectory}")
    private String filesDirectory;

    @Value("${spring.qrCodesDirectory}")
    private String qrCodesDirectory;

    public ResponseEntity<HttpStatus> addMenu(String menuName, String name, String city, byte[] file) {

        log.info("addMenu method started");

        Restaurant restaurant = restaurantService.findRestaurant(name, city);
        if (restaurant == null) {
            log.error("The corresponding restaurant does not exist");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Menu menu = Menu.builder()
                .menuName(menuName)
                .token(generateToken())
                .restaurant(restaurant)
                .build();

        String menuPath = filesDirectory + "/" + menu.getToken() + "/" + name + "_" + city + ".pdf";
        String qrcodePath = qrCodesDirectory + "/menu/" + menu.getToken() + "/" + name + "_" + city + ".pdf";
        File menuFile = new File(menuPath);
        File fileDirectory = new File(this.filesDirectory + "/" + menu.getToken());
        File qrcodeDirectory = new File(this.qrCodesDirectory + "/" + menu.getToken());


        try {
            fileDirectory.mkdirs();
            qrcodeDirectory.mkdirs();
            FileOutputStream fileOutput = new FileOutputStream(menuFile);
            fileOutput.write(file);
            menu.setFilePath(menuPath);
            menu.setQRCodePath(qrcodePath);
            menuRepository.save(menu);
            restaurant.setMenu(menu);
            restaurantService.save(restaurant);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            log.error("could not write bytes to menu file ");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> updateMenu(String name, String city, byte[] file){
        log.info("updateMenu method started");

        Restaurant restaurant = restaurantService.findRestaurant(name, city);
        if (restaurant == null) {
            log.error("The corresponding restaurant does not exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Menu menu = menuRepository.findByMenuId(restaurant.getMenu().getMenuId()).orElse(null);
        if (menu == null) {
            log.error("menu does not exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        File menuFile = new File(menu.getFilePath());
        try {
            FileOutputStream fileOutput = new FileOutputStream(menuFile);
            fileOutput.write(file);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            log.error("could not write bytes to menu file ");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public Menu findMenuById(Long menuId){
        return menuRepository.findByMenuId(menuId).orElse(null);
    }


    private String generateToken() {

        UUID randomId = UUID.randomUUID();

        return randomId.toString().replace("-", "");
    }

}
