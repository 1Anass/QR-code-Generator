package com.controllers;

import com.services.QRCodeGenerationService;
import com.services.ReadQRCodeContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generateQRcode")
public class QRCodeGenerationController {

    private final QRCodeGenerationService qrCodeForURLService;


    @GetMapping("/linkWithMenu")
    public ResponseEntity<HttpStatus> generateQRCodeForURL(@RequestParam String name, @RequestParam String city){
         return qrCodeForURLService.generateQRCode(name, city);
    }

}
