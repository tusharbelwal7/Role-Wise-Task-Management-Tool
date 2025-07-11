package com.ums.usermanagementportal.repo;

import com.ums.usermanagementportal.entity.tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TaskRepo extends JpaRepository<tasks,Integer> {

    @Query("SELECT t FROM tasks t JOIN user u ON t.taskReceiver = u.email WHERE u.id = :userId")
    List<tasks> findTasksByUserId(@Param("userId") Integer userId);
}
