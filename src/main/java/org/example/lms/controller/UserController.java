package org.example.lms.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.lms.entity.Role;
import org.example.lms.entity.User;
import org.example.lms.serviceImp.UserServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImp userServiceImp;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userServiceImp.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userServiceImp.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> addUser(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userServiceImp.saveRole(role));
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserFrom from) {
        userServiceImp.addRoleToUser(from.getUsername(), from.getRoleName());
        return ResponseEntity.ok().build();
    }



}


@Data
class RoleToUserFrom{
    private String username;
    private String roleName;
}
