package com.ankit.realDoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ankit.realDoc.dto.DocumentDTO;
import com.ankit.realDoc.entity.document;
import com.ankit.realDoc.entity.documentVersion;
import com.ankit.realDoc.entity.user;
import com.ankit.realDoc.service.DocumentService;
import com.ankit.realDoc.service.DocumentVersionService;
import com.ankit.realDoc.service.UserService;

import java.util.List;

@Controller
public class DocumentWebSocketController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentVersionService docVersion;

    @Autowired
    private UserService userService;

    @MessageMapping("/edit")
    @SendTo("/topic/updates")
    public documentVersion editDocument(document doc, user editor, String content) {
        if(doc == null || editor == null || content == null) {
            throw new RuntimeException("Invalid Request");
        }
        if(documentService.getDocumentById(doc.getDocId()) == null) {
            throw new RuntimeException("Document Doesn't Exist with docId:"+doc.getDocId());
        }
        if(userService.getUserById(editor.getUserId()) == null) {
            throw new RuntimeException("User Doesn't Exist with userId:"+editor.getUserId());
        }
        document ogDoc = documentService.getDocumentById(doc.getDocId());
        if(!ogDoc.getTitle().equals(doc.getTitle())){
            ogDoc.setTitle(doc.getTitle());
            documentService.updateDocument(doc.getDocId(), doc.getTitle());
        }

        // Create a new version of the document
        documentVersion docversion = docVersion.createVersion(doc.getDocId(), content, editor.getUserId());

        
        return docversion;
    }

    @GetMapping("/{documentId}/versions")
    public List<documentVersion> getVersionsByDocument(@PathVariable Long documentId) {
        return docVersion.getVersionsByDocument(documentId);
    }
}
