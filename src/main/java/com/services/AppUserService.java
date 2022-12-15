package com.services;

import com.data.AppUser;
import com.data.Authority;
import com.repositories.AppUserRepository;
import com.repositories.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService {

    private final AppUserRepository appUserRepository;

    private final AuthorityRepository authorityRepository;

    public boolean createSuperAdmin(String username, String password, String firstName, String lastName,
                                    String emailAddress) {

        if (!authorityRepository.findByAuthority("ROLE_SUPERADMIN").isEmpty())
            return false;

        String[] authorities = {"ROLE_SUPERADMIN"};
        create(username, password, firstName, lastName, emailAddress, authorities);
        return true;
    }

    public void createAdmin(String username, String password, String firstName, String lastName, String emailAddress) {

        String[] authorities = {"ROLE_ADMIN"};
        create(username, password, firstName, lastName, emailAddress, authorities);
    }

    public void createUser(String username, String password, String firstName, String lastName, String emailAddress) {

        String[] authorities = {"ROLE_USER"};
        create(username, password, firstName, lastName, emailAddress, authorities);
    }

    private void create(String userName, String password, String firstName, String lastName, String email,
                        String[] authorities) {

        AppUser user = new AppUser(userName, firstName, lastName, email, password);
        appUserRepository.save(user);
        for (String auth : authorities) {
            Authority authority = new Authority(userName, auth);
            authorityRepository.save(authority);
        }
    }

    public void update(long userId, String password, String newPassword, String firstName, String lastName,
                       String emailAddress) {
        Optional<AppUser> appUser = appUserRepository.findById(userId);

        if (appUser.isEmpty()) {
            log.debug("User not found in database");
            return;
        } else {
            AppUser user = appUser.get();
            if (password != null && (newPassword == null
                    || !(new BCryptPasswordEncoder().encode(password).equals(user.getPassword())))) {
                return;
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
        }


    }


}
