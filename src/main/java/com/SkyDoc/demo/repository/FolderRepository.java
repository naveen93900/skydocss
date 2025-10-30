package com.SkyDoc.demo.repository;

import com.SkyDoc.demo.dto.FolderDTO;
import com.SkyDoc.demo.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    
    // Fetch folders by parent (null for top-level folders)
    List<Folder> findByParentIsNull();

    List<Folder> findByParentId(Long parentId);

    // Optional: fetch only non-deleted folders
    List<Folder> findByIsDeletedFalse();
    
    @Query("SELECT f FROM Folder f LEFT JOIN FETCH f.children")
    List<Folder> findAllWithChildren();
    
    
    
    @Query("SELECT f FROM Folder f WHERE f.isDeleted = false ORDER BY f.id ASC")
    List<Folder> findAllActive();

    @Query("""
            SELECT new com.SkyDoc.demo.dto.FolderDTO(
                f.id, f.name,
                CASE WHEN f.parent IS NOT NULL THEN f.parent.id ELSE null END,
                f.isDeleted, f.createdAt
            )
            FROM Folder f
            WHERE f.isDeleted = false
            ORDER BY f.createdAt ASC
        """)
        List<FolderDTO> findAllActiveFolders();
}
