package com.linhnt.taskmanagementservice.entity;

import com.linhnt.taskmanagementservice.dto.enums.TaskStatus;
import com.linhnt.taskmanagementservice.dto.enums.TaskType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.OPEN;

    private LocalDateTime createdAt;

    private Boolean deleteFlag;

    @ManyToOne
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType taskType;

    //Extend field
    private Integer severity;

    private String stepsToReproduce;

    private String businessValue;

    private LocalDate deadline;

    public static void resetExtendField(TaskEntity taskEntity) {
        taskEntity.severity = null;
        taskEntity.stepsToReproduce = null;
        taskEntity.businessValue = null;
        taskEntity.deadline = null;
    }
}
