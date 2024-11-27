package com.ankit.realDoc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankit.realDoc.entity.documentPermission;

@Repository
public interface documentPermissionRepo extends JpaRepository<documentPermission,Long>{

    List<documentPermission> findByDocId(Long documentId);

    List<documentPermission> findByUserId(Long userId);

    Optional<documentPermission> findByDocIdAndUserId(Long documentId, Long userId);
    
}
