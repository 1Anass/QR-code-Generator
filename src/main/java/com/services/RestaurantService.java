package com.services;

import com.data.entities.AppUser;
import com.data.entities.Restaurant;
import com.data.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {

    private final AppUserService appUserService;
    private final RestaurantRepository restaurantRepository;

    public ResponseEntity<HttpStatus> addRestaurant(Restaurant restaurantDto) {

        log.debug("addRestaurant method started");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        AppUser user = appUserService.findByUsername(username);

        Restaurant restaurant = Restaurant.builder()
                .name(restaurantDto.getName())
                .city(restaurantDto.getCity())
                .address(restaurantDto.getAddress())
                .serviceType(restaurantDto.getServiceType())
                .numberOfTables(Integer.valueOf(restaurantDto.getNumberOfTables()))
                .maxCapacity(Integer.valueOf(restaurantDto.getMaxCapacity()))
                .appUser(user)
                .build();

        restaurantRepository.save(restaurant);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<HttpStatus> updateRestaurant(Restaurant restaurantDto) {

        log.debug("updateRestaurant method started");

        boolean flag = false;
        List<Restaurant> restaurants = findRestaurantByUser();
        Restaurant restaurant = restaurants.stream()
                .filter(res -> res.getRestaurantId() == restaurantDto.getRestaurantId())
                .toList().get(0);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (restaurantDto.getNumberOfTables() != null) {
            restaurant.setNumberOfTables(Integer.valueOf(restaurantDto.getNumberOfTables()));
            flag = true;
            log.debug("number of tables changed");
        }
        if (restaurantDto.getMaxCapacity() != null) {
            restaurant.setMaxCapacity(Integer.valueOf(restaurantDto.getMaxCapacity()));
            flag = true;
            log.debug("maxCapacity changed");
        }
        if (flag == true) {
            restaurantRepository.save(restaurant);
            log.debug("changes saved to DB");
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<HttpStatus> deleteRestaurant(Long id) {
        log.debug("deleteRestaurant method started");
        Restaurant restaurant = findRestaurantById(id);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        restaurantRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Restaurant findRestaurantById(Long id) {
        List<Restaurant> restaurants = findRestaurantByUser();
        return restaurants.stream()
                .filter(res -> res.getRestaurantId() == id)
                .toList().get(0);
    }

    public Restaurant findRestaurant(String name, String city) {
        return restaurantRepository.findByNameAndCity(name, city).orElse(null);
    }

    //security vulnerability ussername argument should be extracted from appcontext info
    public List<Restaurant> findRestaurantByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();

        AppUser user = appUserService.findByUsername(username);
        if (user == null) {
            log.error("User does not exist in DB");
            return null;
        }
        return restaurantRepository.findByAppUserId(user.getId());
    }

    public void save(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

}
