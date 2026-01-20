package com.fpt.edu.lifeform.entity.Parent;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public void initDefault() {
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        initDefault();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
