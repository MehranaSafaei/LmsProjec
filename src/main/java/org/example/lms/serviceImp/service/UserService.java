package org.example.lms.serviceImp.service;

import org.example.lms.entity.Role;
import org.example.lms.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);
//    void  addRoleToUser(String username, String roleName);
    Optional<User> getUserByUsername(String username);
    List<User> getUsers();
}
