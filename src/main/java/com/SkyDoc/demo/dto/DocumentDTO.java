package com.SkyDoc.demo.dto;

import java.time.LocalDateTime;

public class DocumentDTO {
    private Long id;
    private String name;
    private String filePath;
    private String contentType;
    private Long size;
    private Long folderId;
    private LocalDateTime createdAt;

    public DocumentDTO() {}

    // Constructor for JPQL projection
    public DocumentDTO(Long id, String name, String filePath, String contentType, Long size, Long folderId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.contentType = contentType;
        this.size = size;
        this.folderId = folderId;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public Long getSize() { return size; }
    public void setSize(Long size) { this.size = size; }

    public Long getFolderId() { return folderId; }
    public void setFolderId(Long folderId) { this.folderId = folderId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
