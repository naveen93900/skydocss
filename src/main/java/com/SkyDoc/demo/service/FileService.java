//package com.SkyDoc.demo.service;
//
//
//import com.SkyDoc.demo.entity.FileEntity;
//import com.SkyDoc.demo.entity.Folder;
//import com.SkyDoc.demo.repository.FileRepository;
//import com.SkyDoc.demo.repository.folderRepo;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class FileService {
//    private final FileRepository fileRepo;
//
//    public FileService(FileRepository fileRepo) {
//        this.fileRepo = fileRepo;
//    }
//    public FileEntity uploadFile(MultipartFile file, Long folderId) throws IOException {
//        Folder folder = null;
//        if (folderId != null) {
//            // ✅ Use the repository instance, NOT the class
//            folder = folderRepo.findById(folderId).orElseThrow(
//                () -> new RuntimeException("Folder not found with id: " + folderId)
//            );
//        }
//
//        String uploadDir = "uploads";
//        Files.createDirectories(Paths.get(uploadDir));
//        String originalFilename = file.getOriginalFilename();
//        String filePath = Paths.get(uploadDir, System.currentTimeMillis() + "_" + originalFilename).toString();
//        file.transferTo(new File(filePath));
//
//        FileEntity fileEntity = FileEntity.builder()
//                .name(originalFilename)
//                .filePath(filePath)
//                .contentType(file.getContentType())
//                .size(file.getSize())
//                .folder(folder)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        return fileRepo.save(fileEntity);
//    }
//
//    public List<FileEntity> getAll() { return fileRepo.findAll(); }
//
//    public FileEntity create(FileEntity file) { return fileRepo.save(file); }
//
//    public void delete(Long id) { fileRepo.deleteById(id); }
//}
//

package com.SkyDoc.demo.service;

import com.SkyDoc.demo.entity.FileEntity;
import com.SkyDoc.demo.entity.Folder;
import com.SkyDoc.demo.repository.FileRepository;
import com.SkyDoc.demo.repository.FolderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepo;
    private final FolderRepository folderRepo;

    public FileService(FileRepository fileRepo, FolderRepository folderRepo) {
        this.fileRepo = fileRepo;
        this.folderRepo = folderRepo;
    }
    public FileEntity getFileById(Long id) {
        Optional<FileEntity> fileOpt = fileRepo.findById(id);
        return fileOpt.orElse(null);
    }
    public FileEntity uploadFile(MultipartFile file, Long folderId) throws IOException {
        Folder folder = null;
        if (folderId != null) {
            folder = folderRepo.findById(folderId)
                    .orElseThrow(() -> new RuntimeException("Folder not found with id: " + folderId));
        }

        // Use absolute path to avoid Tomcat temp folder issue
        String uploadDir = System.getProperty("user.dir") + "/uploads";
        Files.createDirectories(Paths.get(uploadDir));

        String fileName = Paths.get(file.getOriginalFilename()).getFileName().toString();
        String filePath = Paths.get(uploadDir, fileName).toString();

        file.transferTo(new File(filePath));

        FileEntity fileEntity = FileEntity.builder()
                .name(fileName)
                .filePath(filePath)
                .contentType(file.getContentType())
                .size(file.getSize())
                .folder(folder)
                .createdAt(LocalDateTime.now())
                .build();

        return fileRepo.save(fileEntity);
    }
    
    
  public List<FileEntity> getAll() { return fileRepo.findAll(); }

  public FileEntity create(FileEntity file) { return fileRepo.save(file); }

  public void delete(Long id) { fileRepo.deleteById(id); }
public FileEntity save(FileEntity file) {
	// TODO Auto-generated method stub
	return fileRepo.save(file);
	
}
}



