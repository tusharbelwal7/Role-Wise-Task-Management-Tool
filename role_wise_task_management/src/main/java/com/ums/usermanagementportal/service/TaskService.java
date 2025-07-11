package com.ums.usermanagementportal.service;

import com.ums.usermanagementportal.entity.tasks;
import com.ums.usermanagementportal.repo.TaskRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepo taskRepo;

    public void saveTask(tasks task) {
        try{
            task.setCreatedAt(LocalDateTime.now());
            task.setStatus("Pending");
            taskRepo.save(task);
            logger.info("Task saved successfully. Task ID: {}, Assigned by User ID: {}", task.getTaskId(), task.getTaskAssigner());
        }
        catch(Exception e){
            logger.error("Failed to save task: {}", task, e);
            throw new RuntimeException("Error saving task", e);
        }
    }

    public void completeTask(Integer id, String remark){
        try {
            tasks task = taskRepo.findById(id).orElseThrow(() -> {
                logger.error("Task with ID {} not found.", id);
                return new RuntimeException("Task not found");
            });
            task.setRemark(remark);
            task.setStatus("Completed");
            task.setCompletedAt(LocalDateTime.now());
            taskRepo.save(task);
            logger.info("Task with ID {} marked as completed. Remark: {}", id, remark);
        } catch (RuntimeException e) {
            // Specific handling for runtime exceptions
            logger.error("Runtime exception while completing task with ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            // General exception handling
            logger.error("An unexpected error occurred while completing task with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error completing task", e);
        }
    }

    public List<tasks> getOneTask(Integer id){
        try {
            logger.info("Fetching tasks for User ID: {}", id);
            List<tasks> tasksList = taskRepo.findTasksByUserId(id);
            logger.info("Fetched {} tasks for User ID: {}", tasksList.size(), id);
            return tasksList;
        } catch (Exception e) {
            logger.error("Failed to fetch tasks for User ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error fetching tasks", e);
        }
    }

    public List<tasks> getAllTasks() {
        try {
            logger.info("Fetching all tasks");
            List<tasks> tasksList = taskRepo.findAll();
            logger.info("Fetched {} tasks in total", tasksList.size());
            return tasksList;
        } catch (Exception e) {
            logger.error("Failed to fetch all tasks: {}", e.getMessage(), e);
            throw new RuntimeException("Error fetching all tasks", e);
        }
    }
}

