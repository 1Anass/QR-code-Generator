package com.data;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "authority")
public class Authority {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;


    @Column(name="username")
    @NotNull
    protected String username;

    @Column(name="authority")
    @NotNull
    private String authority;

    protected Authority() { }

    public Authority(String username, String authority){
        this.username = username;
        this.authority = authority;
    }

}
