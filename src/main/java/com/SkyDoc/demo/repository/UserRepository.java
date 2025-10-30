package com.SkyDoc.demo.repository;

import com.SkyDoc.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Find a user by username
    Optional<User> findByUsername(String username);
    
    // Check if a username already exists
    boolean existsByUsername(String username);
    
    // Optionally: find users by email
    Optional<User> findByEmail(String email);
}
