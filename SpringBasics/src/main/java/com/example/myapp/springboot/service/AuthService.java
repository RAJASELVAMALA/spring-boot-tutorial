package com.example.myapp.springboot.service;

import com.example.myapp.springboot.congiguration.JwtUtil;
import com.example.myapp.springboot.model.dao.Role;
import com.example.myapp.springboot.model.dao.User;
import com.example.myapp.springboot.model.dto.AuthResponse;
import com.example.myapp.springboot.model.dto.LoginRequest;
import com.example.myapp.springboot.model.dto.RegisterRequest;
import com.example.myapp.springboot.repository.RoleRepository;
import com.example.myapp.springboot.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

//    public AuthResponse register(RegisterRequest request) {
//        validateUsername(request.getUsername());
//        validatePassword(request.getPassword());
//        checkUserAlreadyExists(request.getUsername());
//
//        User user = new User();
//        user.setUsername(request.getUsername());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        userRepository.save(user);
//
//        return new AuthResponse("User registered successfully, please login to get your token.");
//    }


    public AuthResponse register(RegisterRequest request) {
        validateUsername(request.getUsername());
        validatePassword(request.getPassword());
        checkUserAlreadyExists(request.getUsername());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

//        // 👉 Fetch default role
//        Role defaultRole = roleRepository.findById("ROLE_USER")
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Default role not found"));
//
//        // 👉 Set the role to the user
//        user.setRoles(Set.of(defaultRole)); // Make sure `roles` field is Set<Role>

        userRepository.save(user);

        return new AuthResponse("User registered successfully, please login to get your token.");
    }


    public AuthResponse login(LoginRequest request) {
        validateUsername(request.getUsername());
        validatePassword(request.getPassword());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    // --- 🔒 PRIVATE VALIDATION METHODS BELOW ---

    private void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be blank.");
        }
        if (!username.matches("^[a-zA-Z0-9._-]{3,}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username format. Minimum 3 characters; only letters, digits, dot (.), dash (-), and underscore (_).");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must not be empty.");
        }

        if (password.length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters long.");
        }

        if (!password.matches("^(?=.*[A-Z])(?=.*[0-9]).+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must contain at least one uppercase letter and one number.");
        }
    }

    private void checkUserAlreadyExists(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists.");
        }
    }
}

