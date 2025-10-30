package com.SkyDoc.demo.service;

import com.SkyDoc.demo.entity.User;
import com.SkyDoc.demo.entity.UserAuthority;
import com.SkyDoc.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Update an existing user
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id); // fetch existing user

        // ✅ Update basic fields
        user.setEmployeeName(userDetails.getEmployeeName());
        user.setEmployeeId(userDetails.getEmployeeId());
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());

        // ✅ Initialize authorities if null
        if (user.getAuthorities() == null) {
            user.setAuthorities(new HashSet<>());
        }

        if (userDetails.getAuthorities() == null) {
            userDetails.setAuthorities(new HashSet<>());
        }

        // ✅ Sync authorities
        // Remove authorities that are no longer present
        user.getAuthorities().removeIf(auth -> 
            userDetails.getAuthorities().stream().noneMatch(a -> a.getId().equals(auth.getId()))
        );

        // Add or update authorities from userDetails
        for (UserAuthority auth : userDetails.getAuthorities()) {
            if (user.getAuthorities().stream().noneMatch(a -> a.getId().equals(auth.getId()))) {
                auth.setUser(user); // set the owning side
                user.getAuthorities().add(auth);
            }
        }

        // ✅ Save updated user
        return userRepository.save(user);
    }


    // Delete a user
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
