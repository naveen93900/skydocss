package com.SkyDoc.demo.service;

import com.SkyDoc.demo.dto.DocumentDTO;
import com.SkyDoc.demo.dto.FolderDTO;
import com.SkyDoc.demo.entity.FileEntity;
import com.SkyDoc.demo.entity.Folder;
import com.SkyDoc.demo.repository.FileRepository;
import com.SkyDoc.demo.repository.FolderRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FolderService {

    private final FolderRepository folderRepo;
    private final FileRepository fileRepo;

    public FolderService(FolderRepository folderRepo, FileRepository fileRepo) {
        this.folderRepo = folderRepo;
        this.fileRepo = fileRepo;
    }


    // ✅ Fetch all folders and build a tree structure
//    public List<Folder> getFolderTree() {
//        List<Folder> allFolders = folderRepo.findAll();
//
//        // Map each folder by its ID
//        Map<Long, Folder> folderMap = allFolders.stream()
//                .collect(Collectors.toMap(Folder::getId, f -> f));
//
//        // Prepare the roots list
//        List<Folder> roots = new ArrayList<>();
//
//        for (Folder folder : allFolders) {
//            Folder parent = folder.getParent();
//
//            if (parent != null) {
//                Folder parentRef = folderMap.get(parent.getId());
//                if (parentRef.getChildren() == null)
//                    parentRef.setChildren(new ArrayList<>());
//
//                parentRef.getChildren().add(folder);
//            } else {
//                roots.add(folder);
//            }
//        }
//
//        return roots;
//    }

    // ✅ Create a folder (supports parent)
    public Folder createFolder(String name, Long parentId) {
        Folder folder = new Folder();
        folder.setName(name);

        if (parentId != null) {
            Folder parent = folderRepo.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Parent folder not found"));
            folder.setParent(parent);
        }

        return folderRepo.save(folder);
    }

//    public void delete(Long id) {
//        folderRepo.deleteById(id);
//    }
    
    @Transactional
    public void delete(Long id) {
        Folder folder = folderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        softDeleteFolder(folder);
    }

    private void softDeleteFolder(Folder folder) {
        folder.setIsDeleted(true);
        folderRepo.save(folder);

        // Delete all files in this folder
        List<FileEntity> files = fileRepo.findByFolder(folder);
        for (FileEntity f : files) {
            f.setIsDeleted(true);
            fileRepo.save(f);
        }

        // Recursively delete child folders
        if (folder.getChildren() != null) {
            for (Folder child : folder.getChildren()) {
                softDeleteFolder(child);
            }
        }
    }
    
//    public List<FolderDTO> getAllFolders() {
//        return folderRepo.findAllActiveFolders();
//    }
    
    


        public List<FolderDTO> getAllFoldersTree() {
            // Fetch all active folders
            List<FolderDTO> folders = folderRepo.findAllActiveFolders();

            // Fetch all active documents
            List<DocumentDTO> docs = fileRepo.findAllActiveDocuments();

            // Map folderId → folderDTO for easy lookup
            Map<Long, FolderDTO> folderMap = new HashMap<>();
            folders.forEach(f -> {
                f.setChildren(new ArrayList<>());
                f.setDocuments(new ArrayList<>());
                folderMap.put(f.getId(), f);
            });

            // Assign documents to their folders
            for (DocumentDTO doc : docs) {
                if (doc.getFolderId() != null && folderMap.containsKey(doc.getFolderId())) {
                    folderMap.get(doc.getFolderId()).getDocuments().add(doc);
                }
            }

            // Build tree
            List<FolderDTO> rootFolders = new ArrayList<>();
            for (FolderDTO folder : folders) {
                if (folder.getParentId() != null && folderMap.containsKey(folder.getParentId())) {
                    folderMap.get(folder.getParentId()).getChildren().add(folder);
                } else {
                    rootFolders.add(folder);
                }
            }

            return rootFolders;
        }
    }

    
    

    

    
    
    

