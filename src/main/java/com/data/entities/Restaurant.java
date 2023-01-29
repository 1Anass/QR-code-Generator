package com.data.entities;

import com.data.entities.AppUser;
import com.data.entities.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "restaurant")
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long restaurantId;


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
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Menu menu;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Wifi wifi;

    @Override
    public String toString() {
        return "Restaurant{" +
                ", name='" + getName() + '\'' +
                ", city='" + getCity() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", serviceType='" + getServiceType() + '\'' +
                ", numberOfTables=" + getNumberOfTables() +
                ", maxCapacity=" + getMaxCapacity() +
                ", appUser=" + getAppUser().getUserName() +
                '}';
    }
}
