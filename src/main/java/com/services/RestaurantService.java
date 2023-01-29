package com.services;

import com.data.dtos.RestaurantDTO;
import com.data.entities.AppUser;
import com.data.entities.Restaurant;
import com.data.mappers.RestaurantMapper;
import com.data.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {

    private final AppUserService appUserService;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

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
                .collect(Collectors.toList()).get(0);
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

    public ResponseEntity<HttpStatus> deleteRestaurant(Restaurant restaurantDto) {
        log.debug("deleteRestaurant method started");

        Restaurant restaurant = findRestaurantByUser()
                .stream()
                .filter(res -> res.getRestaurantId() == restaurantDto.getRestaurantId())
                .collect(Collectors.toList()).get(0);

        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        restaurantRepository.deleteById(restaurant.getRestaurantId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> findRestaurantById(Long id) {
        List<Restaurant> restaurants = findRestaurantByUser();
        return new ResponseEntity(restaurants.stream()
                .filter(res -> res.getRestaurantId() == id)
                .collect(Collectors.toList()).get(0), HttpStatus.FOUND);
    }

    public Restaurant findRestaurant(String name, String city) {

        return findRestaurantByUser()
                .stream()
                .filter(res -> res.getName().equals(name) && res.getCity().equals(city))
                .collect(Collectors.toList()).get(0);
    }

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

    public ResponseEntity<HttpStatus>retrieveRestaurantByUser() {
        return new ResponseEntity(restaurantMapper.toRestaurantDTOs(findRestaurantByUser()), HttpStatus.FOUND);
    }



    /*public Restaurant findRestaurantByUserAndId(Long id){
        return findRestaurantByUser()
                .stream()
                .filter(res -> res.getRestaurantId() == id)
                .collect(Collectors.toList()).get(0);
    }*/

    // only called to update restaurant record
    public void save(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

}
