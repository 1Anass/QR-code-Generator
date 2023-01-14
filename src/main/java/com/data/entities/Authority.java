package com.data.entities;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "authority")
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long authority_id;

    @Column(name="username", nullable=false, unique =true)
    protected String username;


    @Column(name="authority", nullable=false)
    private String authority;

    public Authority(String username, String authority){
        this.username = username;
        this.authority = authority;
    }

}
