package com.ankit.realDoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ankit.realDoc.entity.documentPermission;
import com.ankit.realDoc.service.DocumentPermissionService;

import java.util.List;

@RestController
@RequestMapping("/document-permissions")
public class DocumentPermissionController {
    @Autowired
    private DocumentPermissionService documentPermissionService;

    @PostMapping
    public documentPermission assignPermission(@RequestBody documentPermission documentPermission) {
        return documentPermissionService.assignPermission(documentPermission);
    }

    @GetMapping("/document/{documentId}")
    public List<documentPermission> getPermissionsByDocument(@PathVariable Long documentId) {
        return documentPermissionService.getPermissionsByDocument(documentId);
    }

    @GetMapping("/user/{userId}")
    public List<documentPermission> getPermissionsByUser(@PathVariable Long userId) {
        return documentPermissionService.getPermissionsByUser(userId);
    }

    @DeleteMapping
    public ResponseEntity<Void> removePermission(@RequestParam Long documentId, @RequestParam Long userId) {
        documentPermissionService.removePermission(documentId, userId);
        return ResponseEntity.noContent().build();
    }
}
