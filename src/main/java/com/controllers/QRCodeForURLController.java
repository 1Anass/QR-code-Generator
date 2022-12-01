package com.controllers;

import com.services.QRCodeForURLService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generateQRCode")
public class QRCodeForURLController {

    private final QRCodeForURLService qrCodeForURLService;

    @PostMapping("/linkWithURL")
    @ResponseBody
    public void generateQRCodeForURL(@RequestBody String url){
         qrCodeForURLService.generateQRCodeFromURL(url);
         return;
    }


}
