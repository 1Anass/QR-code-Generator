package com.controllers;

import com.services.QRCodeGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generateQRCode")
public class QRCodeForURLController {

    private final QRCodeGenerationService qrCodeForURLService;

    @GetMapping("/linkWithURL")
    public @ResponseBody byte[] generateQRCodeForURL(@RequestBody byte[] menuFile){
         return qrCodeForURLService.generateQRCode(menuFile);
    }


}
