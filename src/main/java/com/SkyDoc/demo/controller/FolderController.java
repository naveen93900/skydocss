package com.SkyDoc.demo.controller;

import com.SkyDoc.demo.dto.FolderDTO;
import com.SkyDoc.demo.entity.Folder;
import com.SkyDoc.demo.repository.folderRepo;
import com.SkyDoc.demo.service.FolderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/folders")
//@CrossOrigin(origins = "http://localhost:5173")
public class FolderController {

    private final FolderService folderService;
    private final folderRepo folderRepository;

    public FolderController(FolderService folderService, folderRepo folderRepository) {
        this.folderService = folderService;
        this.folderRepository=folderRepository;
    }

    // ✅ Get all folders as a tree
//    @GetMapping
//    public List<Folder> getAllFolders() {
//        return folderService.getFolderTree();
//    }
    @GetMapping
    public List<FolderDTO> getAllFoldersTree() {
        return folderService.getAllFoldersTree();
    }

    
    @GetMapping("/folders/root")
    public List<FolderDTO> getRootFolders() {
        return folderService.getAllFoldersTree()
            .stream()
            .filter(f -> f.getParentId() == null)
            .collect(Collectors.toList());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable("id") Long id) {
        try {
            folderService.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    @GetMapping
//    public List<FolderDTO> getAllFolders() {
//        return folderService.getAllFolders();
//    }
    // ✅ Create a folder
    @PostMapping
    public Folder createFolder(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        Long parentId = payload.get("parentId") != null ? Long.valueOf(payload.get("parentId").toString()) : null;
        return folderService.createFolder(name, parentId);
    }
    
    @PutMapping("/{id}/rename")
    public ResponseEntity<Folder> renameFolder(@PathVariable("id") Long id, @RequestBody Map<String, String> body) {
        String newName = body.get("name");
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Folder not found"));
        folder.setName(newName);
        folderRepository.save(folder);
        return ResponseEntity.ok(folder);
    }


    
    
    
    
    
    
    
    
    
    
    
    
}
