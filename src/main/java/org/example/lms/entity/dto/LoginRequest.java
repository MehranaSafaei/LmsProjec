package org.example.lms.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {

    private String username;
    private String email;
    private String password;

}
