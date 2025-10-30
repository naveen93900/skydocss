package com.SkyDoc.demo.repository;


import com.SkyDoc.demo.entity.FileShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileShareRepository extends JpaRepository<FileShare, Long> {
    List<FileShare> findBySharedWithUserId(Long userId);
    List<FileShare> findByFileId(Long fileId);
}

