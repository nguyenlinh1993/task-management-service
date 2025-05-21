package com.linhnt.taskmanagementservice.repository;

import com.linhnt.taskmanagementservice.dto.enums.TaskStatus;
import com.linhnt.taskmanagementservice.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findAllByDeleteFlag(Boolean deleteFlag);

    Optional<TaskEntity> findByIdAndDeleteFlag(Long id, Boolean deleteFlag);

    @Query("""
            SELECT e FROM TaskEntity e
            WHERE (:status IS NULL OR e.status = :status)
            AND (:userId is NULL OR e.user.id = :userId)
            AND (:name IS NULL OR e.name LIKE %:name%)
            AND e.deleteFlag = FALSE
            """)
    List<TaskEntity> searchByRequest(@Param("status") TaskStatus status,
                                     @Param("userId") Long userId,
                                     @Param("name") String name);
}
