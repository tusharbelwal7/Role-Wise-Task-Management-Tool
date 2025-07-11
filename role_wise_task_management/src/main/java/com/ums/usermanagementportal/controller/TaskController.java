package com.ums.usermanagementportal.controller;

import com.ums.usermanagementportal.entity.tasks;
import com.ums.usermanagementportal.entity.user;
import com.ums.usermanagementportal.service.PermissionsService;
import com.ums.usermanagementportal.service.TaskService;
import com.ums.usermanagementportal.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private PermissionsService permissionsService;
    @Autowired
    private UserService userService;


    @GetMapping("/taskform")
    public String showForm(Model model,HttpSession session) {
        Integer tempid= (Integer) session.getAttribute("userId");
        logger.info("User ID {} is trying to access task form", tempid);
        tasks task = new tasks();
        if(permissionsService.checkUserPermissions(tempid,"assigntask")){
            user assignUser = userService.getUserById(tempid);
            String assignEmail = assignUser.getEmail();
            task.setTaskAssigner(assignEmail);
            List<String> allEmail = userService.getEmailsByRoles("MANAGER","USER");
            model.addAttribute("allemails",allEmail);
            model.addAttribute("tasks", task);
            logger.info("User ID {} authorized to assign task", tempid);
            return "taskform";
        }
        else{
            model.addAttribute("message", "You are not authorized to assign task");
            logger.warn("User ID {} is not authorized to assign task", tempid);
            return "warning";
        }
    }

    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute tasks task,HttpSession session) {
        String temprole=(String) session.getAttribute("userRole");
        logger.info("User with role {} is saving a task", temprole);
        taskService.saveTask(task);

        if(temprole.equalsIgnoreCase("admin")){
            logger.info("Task saved by admin. Redirecting to AdminDashboard");
            return "AdminDashboard";
        } else if(temprole.equalsIgnoreCase("manager")) {
            logger.info("Task saved by manager. Redirecting to ManagerDashboard");
            return "ManagerDashboard";
        }
        return "signIn";
    }

    @GetMapping("/getTasks")
    public String getTasks(Model model, HttpSession session) {
        Integer id=(Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("userRole");

        logger.info("Fetching tasks for user ID {} with role {}", id, role);

        if(role.equalsIgnoreCase("admin")){
            model.addAttribute("tasksList", taskService.getAllTasks());
            logger.info("Admin fetched all tasks");
        }
        else if(role.equalsIgnoreCase("user")|role.equalsIgnoreCase("manager")){
            model.addAttribute("tasksList",taskService.getOneTask(id));
            logger.info("User or Manager fetched their tasks");
        }
        return "ViewTask";
    }

    @GetMapping("/do/{taskId}")
    public String doTask(@PathVariable("taskId") Integer taskId, Model model) {
        logger.info("Displaying task form to complete task ID {}", taskId);
        model.addAttribute("taskId", taskId);
        return "DoTask";
    }

    @PostMapping("/complete/{taskId}")
    public String completeTask(@PathVariable("taskId") Integer taskId, @RequestParam("remark") String remark) {
        logger.info("Completing task ID {} with remark: {}", taskId, remark);
        taskService.completeTask(taskId, remark);
        return "redirect:/getTasks";
    }

}

