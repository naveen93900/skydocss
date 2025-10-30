package com.SkyDoc.demo.repository;

import com.SkyDoc.demo.dto.FolderDTO;
import com.SkyDoc.demo.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface folderRepo extends JpaRepository<Folder, Long> {

    // Optional: find all folders by parent
    List<Folder> findByParentId(Long parentId);

    // Optional: find all folders that are not deleted
    List<Folder> findByIsDeletedFalse();
    
    @Query("SELECT f FROM Folder f LEFT JOIN FETCH f.children")
    List<Folder> findAllWithChildren();
    
    
    @Query("SELECT new com.SkyDoc.demo.dto.FolderDTO(f.id, f.name, f.parent.id, f.isDeleted, f.createdAt) " +
            "FROM Folder f WHERE f.isDeleted = false")
     List<FolderDTO> findAllActiveFolders();

}
