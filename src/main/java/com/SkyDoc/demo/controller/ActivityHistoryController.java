package com.SkyDoc.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SkyDoc.demo.service.ActivityHistoryService;
import com.SkyDoc.demo.entity.ActivityHistory;

@RestController
@RequestMapping("/api/history")
public class ActivityHistoryController {
    private final ActivityHistoryService service;

    public ActivityHistoryController(ActivityHistoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ActivityHistory> save(@RequestBody ActivityHistory entry) {
    	entry.setTimestamp(LocalDateTime.now());
        ActivityHistory saved = service.save(entry);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<ActivityHistory>> getAll() {
        return ResponseEntity.ok(service.findAllDesc());
    }
}
