package com.example.crud.controller;

import com.example.crud.entity.User;
import com.example.crud.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = service.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No users found");
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        String result = service.saveUser(user);
        if (result.equals("User already exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result); // 409 Conflict
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(result); // 201 Created
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        User user = service.getUser(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No such user found");
        } else {
            return ResponseEntity.ok(user);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = service.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok("User successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }
    }


    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody User user)
    {
        User updated = service.updateUser(id, user);
        if (updated != null) {
            return "User successfully Updated";
        }
        else
        {
            return "No such user found";
       }
    }



}
