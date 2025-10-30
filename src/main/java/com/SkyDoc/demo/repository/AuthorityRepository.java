package com.SkyDoc.demo.repository;

import com.SkyDoc.demo.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    // Optional: find an authority by name
    Optional<Authority> findByName(String name);
}
