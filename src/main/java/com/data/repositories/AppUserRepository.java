package com.data.repositories;

import com.data.entities.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, String> {

    public Optional<AppUser> findById(long id);

    public Optional<AppUser> findByUserName(String userName);

}
