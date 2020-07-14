package com.study.googlestorage.repository;

import com.study.googlestorage.entity.FileEntity;
import com.study.googlestorage.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByUser(UserEntity UserId);
}
