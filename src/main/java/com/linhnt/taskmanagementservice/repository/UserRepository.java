package com.linhnt.taskmanagementservice.repository;

import com.linhnt.taskmanagementservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);

    boolean existsByIdAndDeleteFlag(Long id, Boolean deleteFlag);

    List<UserEntity> findAllByDeleteFlag(Boolean deleteFlag);

    Optional<UserEntity> findByIdAndDeleteFlag(Long id, Boolean deleteFlag);
}
