package com.ankit.realDoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ankit.realDoc.entity.user;
import com.ankit.realDoc.repository.userRepo;

@Service
public class UserService {
    
    @Autowired
    private userRepo UserRepository;

    //Create
    public user createUser(user User){
        return UserRepository.save(User);
    }
    //Read user by ID
    public user getUserById(Long userId){
        return UserRepository.findById(userId).orElseThrow(()->new RuntimeException("UserNotFound"));
    }
    //Read all user
    public List<user> getAllusers(){
        return UserRepository.findAll();
    }
    //Update
    public user updateUser(Long userId, user User){
        user existingUser = getUserById(userId);
        existingUser.setUserName(User.getUserName());
        existingUser.setEmail(User.getEmail());
        existingUser.setPassword(User.getPassword());

        return UserRepository.save(existingUser);
    }
    //Delete
    public void deleteUserbyId(Long userId){
        UserRepository.deleteById(userId);
    }

    // find user by Email
    public user findUserByEmail(String email){
        return UserRepository.findUserByEmail(email).orElseThrow(()->new RuntimeException("user not found"));
    }

    // Read users by role
    public List<user> getUsersByRole(String role) {
        return UserRepository.findByRole(role);
    }

    // Update user password
    public void updateUserPassword(Long userId, String newPassword) {
        user existingUser = getUserById(userId);
        existingUser.setPassword(newPassword);
        UserRepository.save(existingUser);
    }
}
