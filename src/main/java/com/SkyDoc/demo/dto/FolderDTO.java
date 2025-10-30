package com.SkyDoc.demo.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FolderDTO {
    private Long id;
    private String name;
    private Long parentId;
    private boolean deleted;
    private LocalDateTime createdAt;
    private List<FolderDTO> children = new ArrayList<>();
    private List<DocumentDTO> documents = new ArrayList<>();

    public FolderDTO() {}

    public FolderDTO(Long id, String name, Long parentId, boolean deleted, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.deleted = deleted;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<FolderDTO> getChildren() { return children; }
    public void setChildren(List<FolderDTO> children) { this.children = children; }

    public List<DocumentDTO> getDocuments() { return documents; }
    public void setDocuments(List<DocumentDTO> documents) { this.documents = documents; }
}
