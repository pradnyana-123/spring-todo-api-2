package com.example.spring_todo_api.controller;

import com.example.spring_todo_api.entity.User;
import com.example.spring_todo_api.model.RegisterUserRequest;
import com.example.spring_todo_api.model.WebResponse;
import com.example.spring_todo_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE,  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<Object>> register(@RequestBody RegisterUserRequest data) {
       userService.create(data);

        return new ResponseEntity<>(WebResponse.builder().message("User created").status(201).data(data).build(), HttpStatus.OK);
    }

    @GetMapping(path = "/api/users")
    public ResponseEntity<WebResponse<Object>> findAll() {
       List<User> users = userService.getAll();

       return new ResponseEntity<>(WebResponse.builder().message("Users found").status(200).data(users).build(), HttpStatus.OK);
    }

    @GetMapping(path = "/api/users/{id}")
    public ResponseEntity<WebResponse<Object>> findById(@PathVariable Integer id) {
        Object data = userService.get(id);

        return new ResponseEntity<>(WebResponse.builder().message("User found").status(200).data(data).build(), HttpStatus.OK);

    }
}
