package org.example.lms.entity.mapper;

import org.example.lms.entity.User;
import org.example.lms.entity.dto.LoginRequest;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper {

    public User convertDtoToEntity(LoginRequest registrationRequest) {
        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(registrationRequest.getPassword());
        return user;
    }
//
//    public LoginRequest convertEntityToDto(User user) {
//        return new LoginRequest(user.getPassword(), user.getEmail());
//    }

}
