package com.services;

import com.data.AppUser;
import com.data.Authority;
import com.repositories.AppUserRepository;
import com.repositories.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    private final AuthorityRepository authorityRepository;

    public ResponseEntity<HttpStatus> createSuperAdmin(String username, String password, String firstName, String lastName,
                                                       String emailAddress) {

        if (!authorityRepository.findByAuthority("ROLE_SUPERADMIN").isEmpty())
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);

        String[] authorities = {"ROLE_SUPERADMIN"};
        return create(username, password, firstName, lastName, emailAddress, authorities);

    }

    public ResponseEntity<HttpStatus> createAdmin(String username, String password, String firstName, String lastName, String emailAddress) {

        String[] authorities = {"ROLE_ADMIN"};
        return create(username, password, firstName, lastName, emailAddress, authorities);
    }

    public ResponseEntity<HttpStatus> createUser(String username, String password, String firstName, String lastName, String emailAddress) {

        String[] authorities = {"ROLE_USER"};
        return create(username, password, firstName, lastName, emailAddress, authorities);
    }

    private ResponseEntity<HttpStatus> create(String userName, String password, String firstName, String lastName, String email,
                                              String[] authorities) {

        Set<Authority> auths = new HashSet<>();
        for (String auth : authorities) {
            auths.add(new Authority(userName, auth));
        }
        AppUser user = new AppUser(userName, firstName, lastName, email, password, auths);
        appUserRepository.save(user);
        auths.stream().forEach(auth -> authorityRepository.save(auth));

        return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);

    }

    public ResponseEntity<HttpStatus> update(long userId, String password, String newPassword, String firstName, String lastName,
                       String emailAddress) {
        Optional<AppUser> appUser = appUserRepository.findById(userId);

        if (appUser.isEmpty()) {
            log.debug("User not found in database");
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
        } else {
            AppUser user = appUser.get();
            if (password != null && (newPassword == null
                    || !(new BCryptPasswordEncoder().encode(password).equals(user.getPassword())))) {
                return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
            }

            if (newPassword != null)
                user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            if (firstName != null)
                user.setFirstName(firstName);
            if (lastName != null)
                user.setLastName(lastName);
            if (emailAddress != null)
                user.setEmail(emailAddress);

            appUserRepository.save(user);

            return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUserName(username).orElse(null);
        if (Objects.isNull(user)) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User Not found in the database");
        } else {
            log.info("User Found In The Database with Username: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getAuthorities().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }

}
