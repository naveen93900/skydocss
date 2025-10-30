package com.SkyDoc.demo.controller;

import com.SkyDoc.demo.dto.UserSessionDTO;
import com.SkyDoc.demo.entity.User;
import com.SkyDoc.demo.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ✅ Register new user
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        User saved = authService.register(user);
        return Map.of("message", "User registered successfully", "user", saved);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body, HttpSession session) {
        String username = body.get("username");
        String password = body.get("password");

        Optional<User> userOpt = authService.login(username, password);
        if (userOpt.isEmpty()) {
            return Map.of("error", "Invalid credentials");
        }

        // Store a Serializable DTO instead of the entity
        UserSessionDTO sessionUser = new UserSessionDTO(userOpt.get());
        session.setAttribute("user", sessionUser);

        return Map.of("message", "Login successful", "user", userOpt.get());
    }



    // ✅ Logout user
    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();
        return Map.of("message", "Logged out successfully");
    }

    // ✅ Check current session
    @GetMapping("/whoami")
    public Object whoami(HttpSession session) {
        return session.getAttribute("user"); // This will now return the DTO
    }

}
