package com.SkyDoc.demo.service;



import com.SkyDoc.demo.entity.ActivityHistory;
import com.SkyDoc.demo.repository.ActivityHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityHistoryService {
 private final ActivityHistoryRepository repo;

 public ActivityHistoryService(ActivityHistoryRepository repo) {
     this.repo = repo;
 }

 public ActivityHistory save(ActivityHistory entry) {
     return repo.save(entry);
 }

 public List<ActivityHistory> findAllDesc() {
     return repo.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "timestamp"));
 }
}
