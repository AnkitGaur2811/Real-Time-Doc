package com.ankit.realDoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ankit.realDoc.entity.documentVersion;
import com.ankit.realDoc.service.DocumentVersionService;

import java.util.List;

@RestController
@RequestMapping("/document-versions")
public class DocumentVersionController {
    @Autowired
    private DocumentVersionService documentVersionService;

    @GetMapping("/{documentId}")
    public List<documentVersion> getVersionsByDocument(@PathVariable Long documentId) {
        return documentVersionService.getVersionsByDocument(documentId);
    }

    @PostMapping
    public documentVersion createVersion(@RequestBody documentVersion documentDTO ) {
        return documentVersionService.createVersion(documentDTO.getDoc().getDocId(), documentDTO.getContent(), documentDTO.getEditedBy().getUserId());
    }

    @DeleteMapping("/{versionId}")
    public ResponseEntity<Void> deleteVersion(@PathVariable Long versionId) {
        documentVersionService.deleteVersion(versionId);
        return ResponseEntity.noContent().build();
    }
}
