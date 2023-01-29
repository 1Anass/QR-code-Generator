package com.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "menu")
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long menuId;


    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "token")
    private String token;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "qrcode_path")
    private String QRCodePath;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "modification_date")
    private LocalDateTime modificationDate;


    @Override
    public String toString() {
        return "Menu{" +
                "menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", token='" + token + '\'' +
                ", filePath='" + filePath + '\'' +
                ", qrcodePath='" + QRCodePath + '\'' +
                ", status=" + status +
                ", creationDate=" + creationDate + '\'' +
                '}';
    }


}
