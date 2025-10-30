package com.SkyDoc.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "file_shares")
public class FileShare {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long fileId;
    private Long sharedWithUserId;
    private Long sharedBy;
    private LocalDateTime sharedAt = LocalDateTime.now();
}
