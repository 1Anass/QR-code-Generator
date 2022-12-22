package com.repositories;

import com.data.AppUser;
import com.data.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, String> {

    public List<Authority> findByAuthority(String authority);

    public List<Authority> findByUsername(String username);

    boolean  existsCurrentAccountByAuthority(String name);
}
