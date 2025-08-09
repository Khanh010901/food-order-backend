package com.example.foodorder.controller;

import com.example.foodorder.dto.LoginRequest;
import com.example.foodorder.dto.LoginResponse;
import com.example.foodorder.dto.RegisterRequest;
import com.example.foodorder.model.Profile;
import com.example.foodorder.model.Role;
import com.example.foodorder.model.User;
import com.example.foodorder.repository.RoleRepository;
import com.example.foodorder.repository.UserRepository;
import com.example.foodorder.security.JwtProvider;
import com.example.foodorder.service.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        var authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        var result = authenticationManager.authenticate(authentication);

        UserPrincipal userDetails = (UserPrincipal) result.getPrincipal();
        String token = jwtProvider.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token, userDetails.getUsername()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        // check username/email is exist
        if(userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Username already exists");
        }

        if(userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email already exists");
        }

        // create new user

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ROLE_USER"));
        user.setRoles(Collections.singleton(roleUser));

        Profile profile = new Profile();
        profile.setUser(user);
        user.setProfile(profile);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }


}
