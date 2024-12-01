package com.ankit.realDoc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.realDoc.entity.role;
import com.ankit.realDoc.repository.roleRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private roleRepo roleRepository;

    @GetMapping("/all")
    public ResponseEntity<List<role>> getAllRoles() {
        List<role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<role> getRoleById(@PathVariable Long id) {
        role role = roleRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Role Not Found")
        );
        return ResponseEntity.ok(role);
    }

    @GetMapping("/{roleName}")
    public ResponseEntity<role> getMethodName(@PathVariable String roleName) {
        role role = roleRepository.findByRoleName(roleName).orElseThrow(
            () -> new RuntimeException("role Not found")
        );
        return ResponseEntity.ok(role);
    }
    

}
