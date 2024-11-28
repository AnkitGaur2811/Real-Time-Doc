package com.ankit.realDoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.realDoc.entity.user;
import com.ankit.realDoc.service.AuthService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<user> postMethodName(@RequestBody user User) {
        System.out.println(User.toString());
        user registeredUser = authService.register(User);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        String token = authService.login(email, password);
        return ResponseEntity.ok("Your session token: " + token);
    }

    @GetMapping("/validate")
    public ResponseEntity<Long> validateToken(@RequestParam String token) {
        Long userId = authService.validateToken(token);
        return ResponseEntity.ok(userId);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String token) {
        authService.logout(token);
        return ResponseEntity.ok("Logged out successfully");
    }
    
    
    
}
