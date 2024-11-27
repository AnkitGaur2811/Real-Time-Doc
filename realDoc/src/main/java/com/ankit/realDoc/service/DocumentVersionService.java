package com.ankit.realDoc.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ankit.realDoc.entity.document;
import com.ankit.realDoc.entity.documentVersion;
import com.ankit.realDoc.entity.user;
import com.ankit.realDoc.repository.documentVersionRepo;

@Service
public class DocumentVersionService {
    /*createVersion(Long documentId, String content, User editor): Create a new version of a document.
getVersionsByDocument(Long documentId): Retrieve all versions of a document.
getVersionById(Long versionId): Retrieve a specific version of a document.
deleteVersion(Long versionId): Delete a specific version. */

    @Autowired
    private documentVersionRepo DocumentVersionRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserService userService;

    public documentVersion createVersion(Long docId, String content, Long editorId){
        document doc = documentService.getDocumentById(docId);
        user editor = userService.getUserById(editorId);

        documentVersion docversion = new documentVersion();
        docversion.setDoc(doc);
        docversion.setContent(content);
        docversion.setEditedBy(editor);
        docversion.setEditedtAt(new Date());
        docversion.setVersionNumber(getNextVersionNumberforDoc(docId));

        return DocumentVersionRepository.save(docversion);

    }

    public int getNextVersionNumberforDoc(Long documentId) {
        document doc = documentService.getDocumentById(documentId);
        List<documentVersion> versions = DocumentVersionRepository.findBydoc(doc);
        return versions.isEmpty() ? 1 : versions.size() + 1;
    }

    public List<documentVersion> getVersionsByDocument(Long documentId) {
        document doc = documentService.getDocumentById(documentId);
        return DocumentVersionRepository.findBydoc(doc);
    }

    public documentVersion getVersionById(Long VersionId){
        return DocumentVersionRepository.findById(VersionId).orElseThrow(()-> new RuntimeException("Version:"+ VersionId + " Not found"));
    }

    public void deleteVersion(Long versionId) {
        if (!DocumentVersionRepository.existsById(versionId)) {
            throw new RuntimeException("DocumentVersion not found with ID: " + versionId);
        }
        DocumentVersionRepository.deleteById(versionId);
    }
}
