package com.ankit.realDoc.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "documentVersion")
public class documentVersion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docversionId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date editedtAt;

    @Column(nullable = false)
    private int versionNumber;

    @ManyToOne
    @JoinColumn(name = "editedBy",nullable = false)
    private user editedBy;

    @ManyToOne
    @JoinColumn(name = "documentId", nullable = false)
    private document doc;

    //getter and setters
    public Long getUserId() {
        return docversionId;
    }

    public void setUserId(Long docversionId) {
        this.docversionId = docversionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getEditedtAt() {
        return editedtAt;
    }

    public void setEditedtAt(Date editedtAt) {
        this.editedtAt = editedtAt;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public user getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(user editedBy) {
        this.editedBy = editedBy;
    }

    public document getDoc() {
        return doc;
    }

    public void setDoc(document doc) {
        this.doc = doc;
    }

    


}
