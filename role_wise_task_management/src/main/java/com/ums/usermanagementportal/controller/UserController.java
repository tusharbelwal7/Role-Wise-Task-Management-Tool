package com.ums.usermanagementportal.controller;

import com.ums.usermanagementportal.entity.user;
import com.ums.usermanagementportal.entity.roles;
import com.ums.usermanagementportal.entity.permissions;
import com.ums.usermanagementportal.service.*;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private User_RoleService user_roleService;
    @Autowired
    private Role_PermissionService role_permissionService;
    @Autowired
    private PermissionsService permissionsService;
    private roles roles;
    private permissions permissions;

    @GetMapping("/signIn")
    public String showSignInForm() {
        logger.info("Rendering sign in form.");
        return "signIn";
    }

    @GetMapping("/submitSignIn")
    public String submitSignIn(@RequestParam(required = false) Integer id, @RequestParam(required = false) String name, Model model, HttpSession session) {

        logger.info("Sign-in attempt with ID: {}, Name: {}", id, name);

        String role = userService.checkId(id);

        session.setAttribute("userId", id);
        session.setAttribute("userRole", role);

        if ("admin".equalsIgnoreCase(role)) {
            logger.info("User with ID: {} has role admin.", id);
            return "AdminDashboard";
        } else if ("user".equalsIgnoreCase(role)) {
            logger.info("User with ID: {} has role user.", id);
            return "UserDashboard";
        } else if("manager".equalsIgnoreCase(role)){
            logger.info("User with ID: {} has role manager.", id);
            return "ManagerDashboard";
        } else {
            logger.warn("User with ID: {} is not registered.", id);
            model.addAttribute("message", "You are not registered");
            return "warning";
        }


    }

    @GetMapping("/getUsers")
    public String getUsers(Model model, HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        Integer id= (Integer) session.getAttribute("userId");
        logger.info("Fetching users for role: {}, ID: {}", role, id);
        if (role.equalsIgnoreCase("user") | role.equalsIgnoreCase("manager")) {
            model.addAttribute("usersList",userService.getUserById(id));
            return "View";
        }
        else if(role.equalsIgnoreCase("admin")){
            model.addAttribute("usersList", userService.getAllUsers());
            return "ViewUsers";
        }
        else{
            logger.warn("User with ID: {} is not authorized to view other users.", id);
            model.addAttribute("message", "You are not authorized to view details of others.");
            return "warning";
        }

    }

    @GetMapping("/addUser")
    public String addUser(Model model, HttpSession session) {
        int tempid = (Integer) session.getAttribute("userId");
        if(permissionsService.checkUserPermissions(tempid,"create")){
            logger.info("Rendering add user form.");
            model.addAttribute("user", new user());
            return "adduser";
        }
        else{
            logger.warn("User with ID: {} is not authorized to add new users.", tempid);
            model.addAttribute("message", "You are not authorized to add new users.");
            return "warning";
        }

    }

    @PostMapping("/saveUser")
    public String saveUser(user user) {
        logger.info("Saving user: {}", user);
        userService.saveOrUpdateUser(user);
        rolesService.assignRole(user.getRole());
        user_roleService.assignValue(user.getId(),rolesService.fetch(user.getRole()));
        role_permissionService.assignValue(rolesService.fetch(user.getRole()));
        return "redirect:/getUsers";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable int id, Model model, HttpSession session) {
        int tempid = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("userRole");

        logger.info("Editing user with ID: {}, Current user ID: {}, Role: {}", id, tempid, role);

        if (!permissionsService.checkUserPermissions(tempid,"update") & role.equalsIgnoreCase("user") & tempid != id) {
            logger.warn("User with ID: {} is not authorized to edit details of user with ID: {}.", tempid, id);
            model.addAttribute("message", "You are not authorized to edit details of others.");
            return "warning";
        }


        model.addAttribute("user", userService.getUserById(id));
        String actionUrl = role.equalsIgnoreCase("admin") ? "/editAdminSaveUser" : "/editAsSaveUser";
        model.addAttribute("actionUrl", actionUrl);
        return "EditUser";
    }

    @PutMapping("/editAsSaveUser")
    public String editAsSaveUser(@ModelAttribute user user) {
        logger.info("Saving edited user: {}", user);
        userService.saveOrUpdateUser(user);
        return "redirect:/getUsers";
    }

    @PutMapping("/editAdminSaveUser")
    public String editAdminSaveUser(@ModelAttribute user user) {
        logger.info("Admin saving edited user: {}", user);
        userService.saveOrUpdateUser(user);
        return "redirect:/getUsers";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable int id, Model model, HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        String r = userService.checkId(id);
        Integer tempid=(Integer) session.getAttribute("userId");

        logger.info("Deleting user with ID: {}, Current user ID: {}, Role: {}", id, tempid, role);

        if(permissionsService.checkUserPermissions(tempid,"delete")){
            if (r.equalsIgnoreCase("admin") & role.equalsIgnoreCase("admin")) {
                logger.warn("Admin attempting to delete their own account with ID: {}", id);
                model.addAttribute("message", "You are admin you cannot delete yourself");
                return "warning";
            }
            userService.deleteUser(id);
            return "redirect:/getUsers";
        }
        else{
            logger.warn("User with ID: {} is not authorized to delete user with ID: {}.", tempid, id);
            model.addAttribute("message", "You are not authorized to delete someone");
            return "warning";
        }
    }

}
