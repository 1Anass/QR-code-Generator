package com.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    @Column(name = "name")
    private String name;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
    @Column(name = "service_type")
    private String serviceType;
    @Column(name = "number_of_tables")
    private Integer numberOfTables;
    @Column(name = "max_capacity")
    private Integer maxCapacity;
    @ManyToOne
    private AppUser appUser;
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<Menu> menus;


}
