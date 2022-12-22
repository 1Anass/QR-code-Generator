package com.services;

import com.data.AppUser;
import com.data.Authority;
import com.repositories.AppUserRepository;
import com.repositories.AuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AppUserRepository userRepository;

    public Authority AddAuthority(Authority authority) {
        log.info("Saving new authority {} to the database", authority.getAuthority());

        return authorityRepository.save(authority);
    }

    public ResponseEntity<HttpStatus> assignAuthorityToAppUser(AppUser user, String authorityName) {
        try{
            if(authorityRepository.existsCurrentAccountByAuthority(authorityName) == false){
                Authority authority = new Authority(user.getUserName(),authorityName);
                authorityRepository.save(authority);
            }
            authorityRepository.findByUsername(user.getUserName()).forEach(auth -> user.getAuthorities().add(auth));
            userRepository.save(user);
            return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
        }catch (Exception exception){
            exception.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
