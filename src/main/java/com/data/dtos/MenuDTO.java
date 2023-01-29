package com.data.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MenuDTO {

    private String menuName;
    private byte[] menuFile;
    private byte[] qrcodeFile;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

}
