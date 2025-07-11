package com.ums.usermanagementportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name="tasks")
public class tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;

    @Column(name = "task_assigner", nullable = false, length = 100)
    private String taskAssigner;

    @Column(name = "task_receiver", nullable = false, length = 100)
    private String taskReceiver;

    @Column(name = "task_description", nullable = false)
    private String taskDescription;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "remark", length = 255)
    private String remark="";

    @Column(name = "completed_at")
    private LocalDateTime completedAt=null;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    public tasks() {}

    public void setTaskAssigner(String taskAssigner) {
        this.taskAssigner = taskAssigner;
    }

    public tasks(Integer taskId, String taskReceiver, String taskAssigner, String taskDescription, LocalDateTime createdAt, String remark, LocalDateTime completedAt, String status) {
        this.taskId = taskId;
        this.taskReceiver = taskReceiver;
        this.taskAssigner = taskAssigner;
        this.taskDescription = taskDescription;
        this.createdAt = createdAt;
        this.remark = remark;
        this.completedAt = completedAt;
        this.status = status;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskAssigner() {
        return taskAssigner;
    }

    public String getTaskReceiver() {
        return taskReceiver;
    }

    public void setTaskReceiver(String taskReceiver) {
        this.taskReceiver = taskReceiver;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
