package com.controllers;

import com.services.ReadQRCodeContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scanQRcode")
public class ReadQRCodeContentController {

    private final ReadQRCodeContentService readQRCodeContentService;

    @GetMapping("/getQRcodeContent")
    public @ResponseBody byte[] scanQrCode(@RequestParam String name, @RequestParam String city){
        return readQRCodeContentService.sendMenu(name, city);
    }
}
