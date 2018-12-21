package com.example.jackrabbit.controller;

import com.example.jackrabbit.controller.entity.UserEntity;
import com.example.jackrabbit.service.UserService;
import org.apache.jackrabbit.api.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.jcr.RepositoryException;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody UserEntity entiy) throws RepositoryException {
        User user = userService.create(entiy.getUsername(), entiy.getPassword());
        return ResponseEntity.ok(user);
    }


    @GetMapping
    public ResponseEntity list() {
        return ResponseEntity.ok(userService.list());
    }
}
