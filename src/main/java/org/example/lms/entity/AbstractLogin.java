package org.example.lms.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@MappedSuperclass
public class AbstractLogin {

    private String email;
    private String password;
    private String mobile;
    private String username;

    public AbstractLogin(String email, String password, String mobile, String username) {
        super();
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.username = username;
    }
}
