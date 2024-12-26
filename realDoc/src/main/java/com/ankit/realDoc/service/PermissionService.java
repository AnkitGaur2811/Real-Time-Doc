package com.ankit.realDoc.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import com.ankit.realDoc.entity.permission;
import com.ankit.realDoc.repository.permissionRepo;

public class PermissionService {
    /*createPermission(Permission permission): Add a new permission.
getAllPermissions(): Fetch all permissions.
getPermissionById(Long permissionId): Retrieve a permission by ID.
getPermissionByName(String permissionName): Fetch a permission by its name. */

    @Autowired
    private permissionRepo PermissionRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_MANAGER')")
    public permission createPermission(permission Permission){
        return PermissionRepository.save(Permission);
    }

    public List<permission> getAllPermissions() {
        return PermissionRepository.findAll();
    }

    public permission getPermissionById(Long permissionId) {
        return PermissionRepository.findById(permissionId)
                .orElseThrow(() -> new RuntimeException("Permission not found with ID: " + permissionId));
    }

    public permission getPermissionByName(String permissionName) {
        return PermissionRepository.findByPermissionName(permissionName)
                .orElseThrow(() -> new RuntimeException("Permission not found with name: " + permissionName));
    }
}
