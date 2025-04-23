package org.example.lms.entity.mapper;

import org.example.lms.entity.User;
import org.example.lms.entity.dto.LoginRequest;

public class RegistrationMapper {

    public User convertDtoToEntity(LoginRequest registrationRequest) {
        User user = new User();
        user.setName(registrationRequest.getEmail());
        user.setPassword(registrationRequest.getPassword());
        return user;
    }

    public LoginRequest convertEntityToDto(User user) {
        return new LoginRequest(user.getPassword(), user.getEmail());
    }

}
