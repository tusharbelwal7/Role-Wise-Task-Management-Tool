package com.ums.usermanagementportal.service;

import com.ums.usermanagementportal.entity.permissions;
import com.ums.usermanagementportal.repo.PermissionsRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionsService {

    @Autowired
    private PermissionsRepo permissionsRepo;

    public boolean checkUserPermissions(Integer userId, String permissionType) {
        List<permissions> permList = permissionsRepo.findPermissionsByUserId(userId);

        switch (permissionType.toLowerCase()) {
            case "read":
                return canRead(permList);
            case "create":
                return canCreate(permList);
            case "update":
                return canUpdate(permList);
            case "delete":
                return canDelete(permList);
            case "assigntask":
                return canAssignTask(permList);
            default:
                return false;
        }
    }

    public boolean canRead(List<permissions> permission) {
        return permission.stream()
                .anyMatch(permissions::getCanRead);
    }

    // Method to check if a user has the 'canCreate' permission
    public boolean canCreate(List<permissions> permission) {
        return permission.stream()
                .anyMatch(permissions::getCanCreate);
    }

    // Method to check if a user has the 'canUpdate' permission
    public boolean canUpdate(List<permissions> permission) {
        return permission.stream()
                .anyMatch(permissions::getCanUpdate);
    }

    // Method to check if a user has the 'canDelete' permission
    public boolean canDelete(List<permissions> permission) {
        return permission.stream()
                .anyMatch(permissions::getCanDelete);
    }

    // Method to check if a user has the 'canAssignTask' permission
    public boolean canAssignTask(List<permissions> permission) {
        return permission.stream()
                .anyMatch(permissions::getCanAssignTask);
    }

    public permissions assignValue(Integer pid){
        permissions permissions=new permissions();
        switch(pid){
            case 111:
                permissions.setPerm_id(111);
                permissions.setCanCreate(true);
                permissions.setCanRead(true);
                permissions.setCanUpdate(true);
                permissions.setCanDelete(true);
                permissions.setCanAssignTask(true);

            case 222:
                permissions.setPerm_id(222);
                permissions.setCanCreate(false);
                permissions.setCanRead(true);
                permissions.setCanUpdate(true);
                permissions.setCanDelete(false);
                permissions.setCanAssignTask(true);

            case 333:
                permissions.setPerm_id(333);
                permissions.setCanCreate(false);
                permissions.setCanRead(true);
                permissions.setCanUpdate(true);
                permissions.setCanDelete(false);
                permissions.setCanAssignTask(false);

        }
        permissionsRepo.save(permissions);
        return permissions;

    }
}
