package com.ankit.realDoc.Util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ankit.realDoc.entity.user;
import com.ankit.realDoc.repository.userRepo;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    
    private final String SECRET_KEY = "This_is_the_JWT_Key_and_this_key_is_for_Real_time_doc_project";

    private final SecretKey secretKey;
    
    @Autowired
    private userRepo userRepository;
    
    public JwtUtil() {
        if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            throw new IllegalArgumentException("Secret key is not configured properly");
        }
        secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String GenerateToken(String Email){
        user User = userRepository.findUserByEmail(Email).orElseThrow(
            () -> new RuntimeException("User Not Found")
        );

        return Jwts.builder()
                .setSubject(Email)
                .claim("userId", User.getUserId())
                .claim("role", User.getRole().getRoleName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))//10 hours
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            // Use the parserBuilder() method to validate the token
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);  // Will throw an exception if the token is invalid or expired

            return true;
        } catch (ExpiredJwtException | IllegalArgumentException e) {
            return false; // Token is invalid or expired
        }
    }


}
