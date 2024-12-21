package com.ankit.realDoc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.realDoc.entity.role;
import com.ankit.realDoc.entity.user;
import com.ankit.realDoc.repository.roleRepo;
import com.ankit.realDoc.repository.userRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private roleRepo roleRepository;

    @Autowired
    private userRepo userRepository;

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

    @PostMapping("/role")
    public role createRole(@RequestBody role newRole) {
        // Check if role name is provided
        if(newRole.getRoleName() == null || newRole.getRoleName().isEmpty()) {
            throw new RuntimeException("Role Name is required");
        }
        // Check if role with the same name already exists
        if(roleRepository.findByRoleName(newRole.getRoleName()).isPresent()) {
            throw new RuntimeException("Role with the same name already exists");
        }
        role entity = roleRepository.save(newRole);
        return entity;
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<role> updateRole(@PathVariable Long id, @RequestBody role updatedRole) {
        role existingRole = roleRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Role Not Found")
        );

        if(updatedRole.getRoleName() == null || updatedRole.getRoleName().isEmpty()) {
            throw new RuntimeException("Role Name is required");
        }

        existingRole.setRoleName(updatedRole.getRoleName());
        role savedRole = roleRepository.save(existingRole);
        return ResponseEntity.ok(savedRole);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id, @RequestParam Long newRoleId) {
        role roleToDelete = roleRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Role Not Found")
        );

        role newRole = roleRepository.findById(newRoleId).orElseThrow(
            () -> new RuntimeException("New Role Not Found")
        );

        Optional<user> usersWithRole = userRepository.findByRole(roleToDelete);
        if(usersWithRole.isEmpty()) {
            roleRepository.delete(roleToDelete);
            return ResponseEntity.ok("Role deleted");
        }else {
            List<user> userWithRole = (List<user>) usersWithRole.get();
            for (user User : userWithRole) {
                User.setRole(newRole);
                userRepository.save(User);
            }
            roleRepository.delete(roleToDelete);
            return ResponseEntity.ok("Role deleted and users shifted to new role");
        }
    }

}
