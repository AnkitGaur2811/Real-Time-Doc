package com.ankit.realDoc.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ankit.realDoc.entity.document;
import com.ankit.realDoc.entity.user;
import com.ankit.realDoc.repository.documentRepo;

@Service
public class DocumentService {
    
    @Autowired
    private documentRepo DocumentRepository;

//  Create a new document.
    public document createDocument(String title, user createdBy){
        document doc = new document();
        doc.setTitle(title);
        doc.setCreatedBy(createdBy);
        doc.setCreatedAt(LocalDateTime.now());
        return DocumentRepository.save(doc);
    }
// Retrieve a document by ID.
    public document getDocumentById(Long docId){
        return DocumentRepository.findById(docId).orElseThrow(()-> new RuntimeException("Document Doesn't Exist with docId:"+docId));
    }
// Retrieve all documents.
    public List<document> getAllDocuments(){
        return DocumentRepository.findAll();
    }
// Update the document's title.
    public document updateDocument(Long docId, String newTitle){
        document existingDoc = getDocumentById(docId);
        existingDoc.setTitle(newTitle);
        return DocumentRepository.save(existingDoc);
    }
// Delete a document.
    public void deleteDocument(Long docId){
        DocumentRepository.deleteById(docId);
    }

}
