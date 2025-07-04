package org.example.lms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/secret_resource")
    public ResponseEntity<String> secret(){
        return new ResponseEntity<>("You are viewing my secret" , HttpStatus.OK);
    }

    @GetMapping("/public_resource")
    public ResponseEntity<String> nosecret(){
        return new ResponseEntity<>("You are in public area", HttpStatus.OK);
    }
}
