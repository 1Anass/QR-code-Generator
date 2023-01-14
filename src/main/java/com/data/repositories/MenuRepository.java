package com.data.repositories;

import com.data.entities.Menu;
import com.data.entities.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends CrudRepository<Menu, String> {
    Optional<Menu> findByRestaurant(Restaurant restaurant);

    Optional<Menu> findByMenuId(Long menuId);
}
