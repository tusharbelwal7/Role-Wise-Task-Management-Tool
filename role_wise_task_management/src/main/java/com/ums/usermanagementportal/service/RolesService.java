package com.ums.usermanagementportal.service;

import com.ums.usermanagementportal.entity.roles;
import com.ums.usermanagementportal.repo.RolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolesService {

    @Autowired
    private RolesRepo rolesRepo;

    public Integer fetch(String role){
        return rolesRepo.findIdByRole(role);
    }

    public roles assignRole(String roleName) {
        roles role = new roles();

        switch (roleName.toLowerCase()) {
            case "admin":
                role.setRole_id(100);
                role.setRoles("ADMIN");
                break;

            case "manager":
                role.setRole_id(200);
                role.setRoles("MANAGER");
                break;

            case "user":
                role.setRole_id(300);
                role.setRoles("USER");
                break;



            // Add more cases for other roles
            default:
                throw new IllegalArgumentException("Unknown role: " + roleName);
        }

        rolesRepo.save(role);
        return role;
    }
}
