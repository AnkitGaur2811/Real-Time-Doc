package com.ankit.realDoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import com.ankit.realDoc.entity.documentPermission;
import com.ankit.realDoc.repository.documentPermissionRepo;

public class DocumentPermissionService {
    /*assignPermission(DocumentPermission documentPermission): Assign a permission to a user for a document.
getPermissionsByDocument(Long documentId): Retrieve all user permissions for a specific document.
getPermissionsByUser(Long userId): Get all document permissions for a specific user.
removePermission(Long documentId, Long userId): Revoke a user's permission for a document. */

    @Autowired
    private documentPermissionRepo DocumentPermissionRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_MANAGER')")
    public documentPermission assignPermission(documentPermission DocumentPermission){
        return DocumentPermissionRepository.save(DocumentPermission);
    }

    public List<documentPermission> getPermissionsByDocument(Long documentId){
        return DocumentPermissionRepository.findByDocId(documentId);
    }

    public List<documentPermission> getPermissionsByUser(Long userId){
        return DocumentPermissionRepository.findByUserId(userId);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')||hasRole('ROLE_MANAGER')")
    public void removePermission(Long documentId, Long userId){
        documentPermission docpermission = DocumentPermissionRepository.findByDocIdAndUserId(documentId,userId)
            .orElseThrow(() -> new RuntimeException("Permission not found for Document ID: " + documentId + " and User ID: " + userId));
        DocumentPermissionRepository.delete(docpermission);

    }

}
