package org.example.lms.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.lms.entity.Role;
import org.example.lms.entity.User;
import org.example.lms.repository.RoleRepository;
import org.example.lms.repository.UserRepository;
import org.example.lms.security.JwtTokenProvider;
import org.example.lms.serviceImp.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;


    @PostMapping("/authenticate/{token}")
    public ResponseEntity<?> getAuthentication(@PathVariable String token) {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        user.getRoles().add(defaultRole);
        userRepository.save(user);

        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails).toString();
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

@Data
class SignupRequest {
    private String username;
    private String password;
}

@Data class AuthRequest {
    private String username;
    private String password;
}

@Data @AllArgsConstructor
class AuthResponse {
    private String token;
}

