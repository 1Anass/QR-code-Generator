package com.services;

import com.data.entities.AppUser;
import com.data.entities.Restaurant;
import com.data.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {

    private final AppUserService appUserService;
    private final RestaurantRepository restaurantRepository;

    public ResponseEntity<HttpStatus> addRestaurant(Restaurant restaurantDto, String username) {

        log.debug("addRestaurant method started");

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

    public ResponseEntity<HttpStatus> updateRestaurant(Restaurant restaurantDto){

        log.debug("updateRestaurant method started");

        boolean flag = false;
        Restaurant restaurant = restaurantRepository.findByNameAndCity(restaurantDto.getName(), restaurantDto.getCity()).orElseGet(null);

        if(restaurantDto.getNumberOfTables() != null)
        {
            restaurant.setNumberOfTables(Integer.valueOf(restaurantDto.getNumberOfTables()));
            flag = true;
            log.debug("number of tables changed");
        }
        if(restaurantDto.getMaxCapacity() != null)
        {
            restaurant.setMaxCapacity(Integer.valueOf(restaurantDto.getMaxCapacity()));
            flag = true;
            log.debug("maxCapacity changed");
        }

        if(flag == true){
            restaurantRepository.save(restaurant);
            log.debug("changes saved to DB");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    public ResponseEntity<HttpStatus> deleteRestaurant(Long id, String name, String city){
        log.debug("deleteRestaurant method started");
        Restaurant restaurant = restaurantRepository.findByNameAndCity(name, city).orElse(null);
        if(restaurant == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        restaurantRepository.deleteById(String.valueOf(restaurant.getRestaurantId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Restaurant findRestaurant(String name, String city){
        return restaurantRepository.findByNameAndCity(name, city).orElse(null);
    }

    public void save(Restaurant restaurant){
        restaurantRepository.save(restaurant);
    }

}
