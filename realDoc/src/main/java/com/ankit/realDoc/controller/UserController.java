package com.ankit.realDoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ankit.realDoc.entity.user;
import com.ankit.realDoc.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<user> getAllUsers() {
        return userService.getAllusers();
    }

    @GetMapping("/role/{role}")
    public List<user> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }

    @PutMapping("/{id}/password")
    public void updateUserPassword(@PathVariable Long id, @RequestBody String newPassword) {
        userService.updateUserPassword(id, newPassword);
    }

    @GetMapping("/{id}")
    public user getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
