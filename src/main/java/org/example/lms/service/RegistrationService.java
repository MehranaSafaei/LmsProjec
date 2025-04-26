package org.example.lms.service;

import lombok.AllArgsConstructor;
import org.example.lms.entity.User;
import org.example.lms.entity.dto.LoginRequest;
import org.example.lms.entity.mapper.RegistrationMapper;
import org.example.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

//@Service
//@AllArgsConstructor
//public class RegistrationService {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private RegistrationMapper registrationMapper;
//
//    public String register(LoginRequest registrationRequest, Model model) {
//        if (registrationRequest.getPassword() == null || registrationRequest.getPassword().length() < 8) {
//            throw new IllegalArgumentException("Password must be at least 8 characters long");
//        }
//
//        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("User with this email address already exists");
//        }
//        User user = registrationMapper.convertDtoToEntity(registrationRequest);
//        User savedUser = userRepository.save(user);
//        LoginRequest savedUserDto = registrationMapper.convertEntityToDto(savedUser);
//        model.addAttribute("user", savedUserDto);
//        return "Registration successful";
//    }
//
//
//    public boolean authenticate(String username, String password) {
//        User user = userRepository.findByUsername((username)).orElse(null);
//        return user != null && user.getPassword().equals(password);
//    }
//
//    public boolean userExists(String username) {
//        return userRepository.existsByUsername((username).trim());
//    }
//}
