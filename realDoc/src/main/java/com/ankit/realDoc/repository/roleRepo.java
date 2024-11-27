package com.ankit.realDoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankit.realDoc.entity.role;

import java.util.Optional;


@Repository
public interface roleRepo extends JpaRepository<role,Long>{
    Optional<role> findByRoleName(String roleName);
}
