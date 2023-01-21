package com.data.entities;

import com.utils.AttributeEncryptor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@Table(name = "wifi")
@NoArgsConstructor
@AllArgsConstructor
public class Wifi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wifiId;

    @Column(name = "wifiName")
    private String wifiName;
    @Column(name = "password")
    @Convert(converter = AttributeEncryptor.class)
    private String password;
    @Column(name = "encryption")
    private String encryption;
    @OneToOne(mappedBy = "wifi")
    private Restaurant restaurant;

}
