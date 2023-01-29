package com.data.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WifiDTO {
    private String wifiName;
    private String encryption;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
}
