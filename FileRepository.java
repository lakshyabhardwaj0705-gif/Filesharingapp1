package com.example.filesharingapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.filesharingapp.entity.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    // ✅ Must match entity field EXACTLY
    List<FileEntity> findByExpiryTimeBefore(LocalDateTime time);

}