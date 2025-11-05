package com.SkyDoc.demo.controller;

import com.SkyDoc.demo.dto.UserSessionDTO;
import com.SkyDoc.demo.entity.ActivityHistory;
import com.SkyDoc.demo.entity.User;
import com.SkyDoc.demo.service.ActivityHistoryService;
import com.SkyDoc.demo.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // ✅ enable for frontend
public class AuthController {

    private final AuthService authService;
    private final ActivityHistoryService activityHistoryService;

    public AuthController(AuthService authService, ActivityHistoryService activityHistoryService) {
        this.authService = authService;
        this.activityHistoryService=activityHistoryService;
    }

    // ✅ Register new user
//    @PostMapping("/register")
//    public Map<String, Object> register(@RequestBody User user) {
//        if (authService.usernameExists(user.getUsername())) {
//            return Map.of("error", "Username already taken");
//        }
//
//        User saved = authService.register(user);
//        UserSessionDTO sessionUser = new UserSessionDTO(saved);
//
//        return Map.of(
//                "message", "User registered successfully",
//                "user", sessionUser
//        );
//    }

    // ✅ Login user
//    @PostMapping("/login")
//    public Map<String, Object> login(@RequestBody Map<String, String> body, HttpSession session) {
//        String username = body.get("username");
//        String password = body.get("password");
//
//        Optional<User> userOpt = authService.login(username, password);
//        if (userOpt.isEmpty()) {
//            return Map.of("error", "Invalid username or password");
//        }
//
//        // Store serializable DTO (safe for session)
//        UserSessionDTO sessionUser = new UserSessionDTO(userOpt.get());
//        session.setAttribute("user", sessionUser);
//
//        return Map.of(
//                "message", "Login successful",
//                "user", sessionUser
//        );
//    }

    
    
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body, HttpSession session) {
        String username = body.get("username");
        String password = body.get("password");

        Optional<User> userOpt = authService.login(username, password);
        if (userOpt.isEmpty()) {
            return Map.of("error", "Invalid username or password");
        }

        // Store serializable DTO (safe for session)
        UserSessionDTO sessionUser = new UserSessionDTO(userOpt.get());
        session.setAttribute("user", sessionUser);

        // ✅ Log login activity
        try {
            ActivityHistory loginActivity = new ActivityHistory();
            loginActivity.setUsername(sessionUser.getUsername());
            loginActivity.setRole(sessionUser.getRole());
            loginActivity.setActionType("LOGIN");
            loginActivity.setTargetType("SYSTEM");
            loginActivity.setDetails("User logged in successfully");
            loginActivity.setTimestamp(LocalDateTime.now());

            activityHistoryService.save(loginActivity);
        } catch (Exception e) {
            // Safe fallback — don’t break login if logging fails
            System.err.println("Failed to log login activity: " + e.getMessage());
        }

        return Map.of(
                "message", "Login successful",
                "user", sessionUser
        );
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
        Object user = session.getAttribute("user");
        if (user == null) {
            return Map.of("error", "Not logged in");
        }
        return user;
    }
}
