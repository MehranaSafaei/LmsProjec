package org.example.lms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USERS")
public class User extends AbstractUser {
}
