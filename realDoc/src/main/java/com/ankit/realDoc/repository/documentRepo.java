package com.ankit.realDoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankit.realDoc.entity.document;

@Repository
public interface documentRepo extends JpaRepository<document,Long>{
    
}
