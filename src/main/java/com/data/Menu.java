package com.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "token")
    private String token;

    @ManyToOne
    private Restaurant restaurant;


}
