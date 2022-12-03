package com.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ReadQRCodeContentService {

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

}
