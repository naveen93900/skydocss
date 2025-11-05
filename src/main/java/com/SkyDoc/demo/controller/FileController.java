package com.SkyDoc.demo.controller;

import com.SkyDoc.demo.entity.FileEntity;
import com.SkyDoc.demo.service.FileService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/files")
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    

    @GetMapping
    public List<FileEntity> getAll() {
        return fileService.getAll();
    }

//    @PostMapping(consumes = {"multipart/form-data"})
//    public FileEntity uploadFile(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam(value = "folderId", required = false) Long folderId
//    ) throws IOException {
//        return fileService.uploadFile(file, folderId);
//    }
    
    @PostMapping(consumes = {"multipart/form-data"})
    public FileEntity uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folderId", required = false) Long folderId,
            @RequestParam(value = "issueDate", required = false) String issueDate,
            @RequestParam(value = "issueType", required = false) String issueType,
            @RequestParam(value = "fileName", required = false) String fileName,
            @RequestParam(value = "revisionNumber", required = false) String revisionNumber
    ) throws IOException {
        return fileService.uploadFile(file, folderId, issueDate, issueType, fileName, revisionNumber);
    }


    @GetMapping("/{id}/metadata")
    public ResponseEntity<Map<String, Object>> getFileMetadata(@PathVariable("id") Long id) {
        FileEntity file = fileService.getFileById(id);
        
        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> metadata = Map.of(
            "fileName", file.getName(),
            "issueDate", file.getIssueDate(),
            "issueType", file.getIssueType(),
            "revisionNumber", file.getRevisionNumber()
        );

        return ResponseEntity.ok(metadata);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        fileService.delete(id);
    }
    
    
    @GetMapping("/{id}/content")
    public ResponseEntity<Resource> getFileContent(@PathVariable("id") Long id) {
        FileEntity file = fileService.getFileById(id); // fetch DB record

        if (file == null || file.getIsDeleted()) {
            return ResponseEntity.notFound().build();
        }

        // Use Path to get file from server
        Path path = Paths.get(file.getFilePath());
        if (!Files.exists(path)) return ResponseEntity.notFound().build();

        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        String contentType = file.getContentType() != null ? file.getContentType() : "application/pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
    
    
    @PutMapping("/{id}/rename")
    public ResponseEntity<FileEntity> renameFile(@PathVariable("id") Long id, @RequestBody Map<String, String> body) {
        String newName = body.get("name");
        FileEntity file = fileService.getFileById(id);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        file.setName(newName);
        fileService.save(file);
        return ResponseEntity.ok(file);
    }


    

    
    
}
