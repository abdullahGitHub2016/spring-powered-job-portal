package com.abdullah.bdjobs_backend.controller;

import com.abdullah.bdjobs_backend.entity.User;
import com.abdullah.bdjobs_backend.repository.UserRepository;
import com.abdullah.bdjobs_backend.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already taken!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Ensure a default role is set if missing
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("JOB_SEEKER");
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                // FIXED: Pass both email and role to generateToken
                String role = user.getRole() != null ? user.getRole() : "JOB_SEEKER";
                String token = jwtUtils.generateToken(user.getEmail(), role);

                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "role", role
                ));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }
}