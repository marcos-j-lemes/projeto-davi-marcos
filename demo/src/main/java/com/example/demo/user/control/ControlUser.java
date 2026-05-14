package com.example.demo.user.control;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.ResponseEntity;

import com.example.demo.user.entity.User;
import com.example.demo.user.serveci.UserService;
import com.example.demo.user.dto.CreateUserRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
public class ControlUser {

    @Autowired
    private UserService userservice;


    public ControlUser(UserService userservice) {
        this.userservice = userservice;
    }


    @PostMapping
    public ResponseEntity<User> add(@Valid @RequestBody User user) {
        User savedUser = userservice.add(user);
        return ResponseEntity.ok(savedUser);
    }

}