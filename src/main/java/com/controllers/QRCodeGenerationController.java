package com.controllers;

import com.services.QRCodeGenerationService;
import com.services.ReadQRCodeContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generateQRCode")
public class QRCodeGenerationController {

    private final QRCodeGenerationService qrCodeForURLService;


    @GetMapping("/linkWithDoc")
    public @ResponseBody byte[] generateQRCodeForURL(@RequestBody byte[] menuFile){
         return qrCodeForURLService.generateQRCode(menuFile);
    }




}
