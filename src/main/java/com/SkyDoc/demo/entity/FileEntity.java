package com.SkyDoc.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "files")
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String filePath;
    private String contentType;
    private Long size;
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getFilePath() { return filePath; }
    public String getContentType() { return contentType; }
    public Long getSize() { return size; }
    public boolean getIsDeleted() { return isDeleted; }
    public Folder getFolder() { return folder; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public void setSize(Long size) { this.size = size; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }
    public void setFolder(Folder folder) { this.folder = folder; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
