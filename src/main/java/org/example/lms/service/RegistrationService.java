package org.example.lms.service;

import org.example.lms.entity.dto.RegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    public String register(RegistrationRequest registrationRequest) {
        return "Registration successful";
    }
}
