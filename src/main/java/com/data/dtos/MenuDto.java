package com.data.dtos;

import com.data.entities.Restaurant;

import javax.persistence.Column;
import javax.persistence.OneToOne;

public class MenuDto {

    private String menuName;
    private String token;
    private String filePath;
    private String QRCodePath;
    private Boolean status;

}
