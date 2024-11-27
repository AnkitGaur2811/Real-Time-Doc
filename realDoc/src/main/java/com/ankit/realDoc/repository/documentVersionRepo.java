package com.ankit.realDoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankit.realDoc.entity.document;
import com.ankit.realDoc.entity.documentVersion;

@Repository
public interface documentVersionRepo extends JpaRepository<documentVersion,Long> {
    List<documentVersion> findBydoc(document documentId);
}
