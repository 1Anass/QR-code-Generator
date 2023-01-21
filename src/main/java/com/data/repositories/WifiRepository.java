package com.data.repositories;

import com.data.entities.Wifi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WifiRepository extends CrudRepository<Wifi, String> {

    Optional<Wifi> findByWifiId(Long wifiId);

}
