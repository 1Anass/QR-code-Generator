package com.controllers;

import com.data.entities.AppUser;
import com.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class AppUserController {

    private final AppUserService appUserService;


    @PostMapping("/createUser")
    public boolean createUser(@RequestBody AppUser dto) {

        if (!check(dto))
            return false;

        appUserService.createUser(dto.getUserName(), dto.getPassword(), dto.getFirstName(), dto.getLastName(),
                dto.getEmail());
        return true;
    }

    @PostMapping("/createAdmin")
    public boolean createAdmin(@RequestBody AppUser dto) {

        if (!check(dto))
            return false;

        appUserService.createAdmin(dto.getUserName(), dto.getPassword(), dto.getFirstName(), dto.getLastName(),
                dto.getEmail());
        return true;
    }

    @PostMapping("/createSuperAdmin")
    public boolean createSuperAdmin(@RequestBody AppUser dto) {

        if (!check(dto))
            return false;

        appUserService.createSuperAdmin(dto.getUserName(), dto.getPassword(), dto.getFirstName(), dto.getLastName(),
                dto.getEmail());
        return true;
    }

    private boolean check(AppUser dto) {
        return (dto.getUserName() != null && dto.getPassword() != null && dto.getFirstName() != null
                && dto.getLastName() != null && dto.getEmail() != null );
    }
}
