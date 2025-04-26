package org.example.lms.entity.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String email;
    private String mobile;
    private String password;
    private String confirmPassword;
}
