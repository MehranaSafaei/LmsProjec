package org.example.lms.controller;

import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.example.lms.entity.dto.RegistrationRequest;
import org.example.lms.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    public String register(@RequestBody RegistrationRequest registrationRequest) {
        return  registrationService.register(registrationRequest);
    }



}
