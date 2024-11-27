package com.ankit.realDoc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankit.realDoc.entity.permission;

@Repository
public interface permissionRepo extends JpaRepository<permission,Long> {

    Optional<permission> findByPermissionName(String permissionName);
    
}
