package com.ankit.realDoc.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ankit.realDoc.entity.user;
import com.ankit.realDoc.repository.userRepo;

@Service
public class coustomeuserdetailService implements UserDetailsService {
    
    private final userRepo userRepository;

    public coustomeuserdetailService(userRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user User = userRepository.findByUserName(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Collection<SimpleGrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority(User.getRole().getRoleName())
    );

    return new org.springframework.security.core.userdetails.User(
            User.getUserName(),
            User.getPassword(),
            authorities
    );
    }
}
