package com.SkyDoc.demo.controller;

import com.SkyDoc.demo.dto.UserDTO;
import com.SkyDoc.demo.entity.User;
import com.SkyDoc.demo.repository.UserRepository;
import com.SkyDoc.demo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserController(UserService userService, PasswordEncoder passwordEncoder,UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository=userRepository;
    }

    // ✅ Get all users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers()
                .stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getEmployeeName(),
                        user.getEmployeeId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    // ✅ Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        UserDTO dto = new UserDTO(
                user.getId(),
                user.getEmployeeName(),
                user.getEmployeeId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole());
        return ResponseEntity.ok(dto);
    }
    
    
 // ✅ Get current logged-in user
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return ResponseEntity.ok(user);
    }


    // ✅ Create new user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = new User(
                userDTO.getEmployeeName(),
                userDTO.getEmployeeId(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                userDTO.getRole()
        );

        // store both plain and encoded
        user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));

        User saved = userService.createUser(user);

        UserDTO responseDTO = new UserDTO(
                saved.getId(),
                saved.getEmployeeName(),
                saved.getEmployeeId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getRole());

        return ResponseEntity.ok(responseDTO);
    }

    // ✅ Update existing user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        User existing = userService.getUserById(id);

        existing.setEmployeeName(userDTO.getEmployeeName());
        existing.setEmployeeId(userDTO.getEmployeeId());
        existing.setUsername(userDTO.getUsername());
        existing.setEmail(userDTO.getEmail());
        existing.setRole(userDTO.getRole());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            existing.setPassword(userDTO.getPassword());
            existing.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        }

        User updated = userService.updateUser(id, existing);

        UserDTO responseDTO = new UserDTO(
                updated.getId(),
                updated.getEmployeeName(),
                updated.getEmployeeId(),
                updated.getUsername(),
                updated.getEmail(),
                updated.getRole());

        return ResponseEntity.ok(responseDTO);
    }

    // ✅ Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
    
  
    
    
    
    
    
}
