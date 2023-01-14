package com.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@RequiredArgsConstructor
public class ReadQRCodeContentService {

    private final RestaurantService restaurantService;

    @Value("${spring.filesDirectory}")
    private String filesDirectory;

    public byte[] getFileByToken(String token){

        String filePath = filesDirectory + "/" + token + ".png";
        File file = new File(filePath);

        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public byte[] sendMenu(String name, String city){
        String menuPath = getPath(name, city);

        File menuFile = new File(menuPath);

        try {
            byte[] fileContent = Files.readAllBytes(menuFile.toPath());
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public String getPath(String name, String city){
        return restaurantService.findRestaurant(name, city).getMenu().getFilePath();
    }

}
