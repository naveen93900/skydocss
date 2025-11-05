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

    // ✅ Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Get user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // ✅ Create a new user
    public User createUser(User user) {
        // Ensure authorities are initialized
        if (user.getAuthorities() == null) {
            user.setAuthorities(new HashSet<>());
        }

        return userRepository.save(user);
    }

    // ✅ Update existing user
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id); // fetch existing

        // --- Update basic info ---
        user.setEmployeeName(userDetails.getEmployeeName());
        user.setEmployeeId(userDetails.getEmployeeId());
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());

        // --- Update passwords ---
        if (userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
            user.setPassword(userDetails.getPassword()); // plain password
        }

        if (userDetails.getPasswordHash() != null && !userDetails.getPasswordHash().isBlank()) {
            user.setPasswordHash(userDetails.getPasswordHash()); // encoded password
        }

        // --- Sync authorities safely ---
        if (user.getAuthorities() == null) {
            user.setAuthorities(new HashSet<>());
        }
        if (userDetails.getAuthorities() == null) {
            userDetails.setAuthorities(new HashSet<>());
        }

        // Remove old authorities not present in updated set
        user.getAuthorities().removeIf(existingAuth ->
            userDetails.getAuthorities().stream().noneMatch(a -> a.getId().equals(existingAuth.getId()))
        );

        // Add new authorities
        for (UserAuthority auth : userDetails.getAuthorities()) {
            if (user.getAuthorities().stream().noneMatch(a -> a.getId().equals(auth.getId()))) {
                auth.setUser(user);
                user.getAuthorities().add(auth);
            }
        }

        // --- Save and return updated user ---
        return userRepository.save(user);
    }

    // ✅ Delete a user
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
