package com.data;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "role")
    private String role;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

}
