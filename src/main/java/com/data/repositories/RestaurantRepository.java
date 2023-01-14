package com.data.repositories;

import com.data.entities.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, String> {

    Optional<Restaurant> findByNameAndCity(String name, String city);


}
