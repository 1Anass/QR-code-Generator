package com.data.repositories;

import com.data.entities.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, String> {

    public List<Authority> findByAuthority(String authority);

}
