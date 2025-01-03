package com.ankit.realDoc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankit.realDoc.entity.role;
import com.ankit.realDoc.entity.user;

@Repository
public interface userRepo extends JpaRepository<user, Long> {
    Optional<user> findUserByEmail(String email);

    Optional<user> findByUserName(String userName);

    Optional<user> findByRole(role roleToDelete);

    List<user> findByRole(String role);
}
