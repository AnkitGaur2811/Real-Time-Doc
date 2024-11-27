package com.ankit.realDoc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ankit.realDoc.entity.role;
import com.ankit.realDoc.repository.roleRepo;

@Service
public class RoleService {
    
    @Autowired
    private roleRepo roleRepository;

    public role createRole(role Role){
        return roleRepository.save(Role);
    }

    public role getRoleById(Long roleId){
        return roleRepository.findById(roleId).orElseThrow(()->new RuntimeException("Role not found with ID: " + roleId));
    }

    public role getRoleByName(String roleName){
        return roleRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found with name: " + roleName));
    }
}
