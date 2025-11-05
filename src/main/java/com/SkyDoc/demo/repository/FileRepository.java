package com.SkyDoc.demo.repository;


import com.SkyDoc.demo.dto.DocumentDTO;
import com.SkyDoc.demo.entity.FileEntity;
import com.SkyDoc.demo.entity.Folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByFolderId(Long folderId);
    List<FileEntity> findByFolder(Folder folder);
    
    @Query("""
    	    SELECT new com.SkyDoc.demo.dto.DocumentDTO(
    	        f.id,
    	        f.name,
    	        f.filePath,
    	        f.contentType,
    	        f.size,
    	        f.folder.id,
    	        f.createdAt,
    	        f.issueDate,
    	        f.issueType,
    	        f.revisionNumber
    	    )
    	    FROM FileEntity f
    	    WHERE f.isDeleted = false
    	""")
    	List<DocumentDTO> findAllActiveDocuments();

    }