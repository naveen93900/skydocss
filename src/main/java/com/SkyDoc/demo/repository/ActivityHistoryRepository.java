package com.SkyDoc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SkyDoc.demo.entity.ActivityHistory;
import com.SkyDoc.demo.entity.Authority;

public interface ActivityHistoryRepository extends JpaRepository<ActivityHistory, Long> {

}
