package com.ankit.realDoc.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ankit.realDoc.Util.JwtUtil;
import com.ankit.realDoc.entity.user;
import com.ankit.realDoc.repository.userRepo;

@Service
public class AuthService {
    /*register(User user): Handle user registration.
login(String email, String password): Validate credentials and generate tokens.
validateToken(String token): Validate JWT tokens (will implement JWT later). */

    @Autowired
    private userRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // In-memory token storage (temporary)
    // private final Map<String, Long> activeSessions = new HashMap<>();

    public user register(user User){
        Optional<user> existingUser = userRepository.findUserByEmail(User.getEmail());
        if(existingUser.isPresent()){
            throw new RuntimeException("Email already in use");
        }
        User.setCreatedAt(new Date());
        User.setPassword(passwordEncoder.encode(User.getPassword()));
        return userRepository.save(User);
    }

    public String login(String email, String password){
        user User = userRepository.findUserByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        // System.out.println("======================================================================");
        // System.out.println(password);
        // System.out.println(User.getPassword());
        
        if(passwordEncoder.matches(password,User.getPassword() )){
            return jwtUtil.GenerateToken(email);
        }
        throw new RuntimeException("Invalid email or password");

        // if (!passwordEncoder.matches(password, User.getPassword())) {
        //     throw new RuntimeException("Invalid password");
        // }

        // // Generate and store a temporary session token
        // String token = UUID.randomUUID().toString();
        // activeSessions.put(token, User.getUserId());
        // for (String key : activeSessions.keySet()) {
        //     System.out.println(key+":"+activeSessions.get(key));
        // }
        // return token;
    }

    public Long validateToken(String token) {
        if (jwtUtil.validateToken(token, null)) {
            String email = jwtUtil.extractEmail(token);
            System.out.println(email);
            Optional<user> userOpt = userRepository.findUserByEmail(email);
            System.out.println(userOpt.toString());
            if (userOpt.isPresent()) {
                return userOpt.get().getUserId();
            }
        }
        throw new RuntimeException("Invalid or expired token");
        // if (!activeSessions.containsKey(token)) {
        //     throw new RuntimeException("Invalid or expired token");
        // }
        // return activeSessions.get(token);
    }

    public void logout(String token) {
        // activeSessions.remove(token);
        // System.out.println("ACTIVE SESSION");
        // for (String key : activeSessions.keySet()) {
        //     System.out.println(key+":"+activeSessions.get(key));
        // }
    }

    public void validateRole(String token, String RequiredRole){
        Long userId = validateToken(token);

        user User = userRepository.findById(userId).orElseThrow(
            ()-> new RuntimeException("user Not found")
        );
        String roleName = User.getRole().getRoleName();
        if(!roleName.equalsIgnoreCase(RequiredRole)){
            throw new RuntimeException("Access denied: insufficient permissions");
        }
    }
}
