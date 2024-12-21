package com.ankit.realDoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ankit.realDoc.service.DocumentService;
import com.ankit.realDoc.entity.document;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class Documentcontroller {

    @Autowired
    private DocumentService documentService;

    @GetMapping
    public List<document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/{id}")
    public document getDocumentById(@PathVariable Long id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping
    public document createDocument(@RequestBody document newdocument) {
        return documentService.createDocument(newdocument.getTitle(), newdocument.getCreatedBy());
    }

    @PutMapping("/update/{id}")
    public document updateDocument(@PathVariable Long id, @RequestBody document document) {
        return documentService.updateDocument(id, document.getTitle());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
