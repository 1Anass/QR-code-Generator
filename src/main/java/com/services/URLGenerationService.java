package com.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class URLGenerationService {

    @Value("${spring.domainName}")
    private String domainName;

    @Value("${spring.qrcodeScannerEndpoint}")
    private String scanEndpoint;

    public String generateToken(){

        UUID randomId = UUID.randomUUID();

        return randomId.toString().replace("-","");
    }

    public String generateURL(String token){



        StringBuilder urlBuilder = new StringBuilder();

        urlBuilder.append("http://").append(this.domainName)
                .append(scanEndpoint).append("?token=").append(token);

        return urlBuilder.toString();
    }

}
