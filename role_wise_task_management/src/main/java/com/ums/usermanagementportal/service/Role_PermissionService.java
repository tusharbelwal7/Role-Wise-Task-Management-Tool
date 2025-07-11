package com.ums.usermanagementportal.service;

import com.ums.usermanagementportal.entity.role_permission;
import com.ums.usermanagementportal.repo.Role_PermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Role_PermissionService {
    @Autowired
    private Role_PermissionRepo role_permissionRepo;

    public role_permission assignValue(Integer rid){
        role_permission role_permission = new role_permission();
        switch(rid){
            case 100:
                role_permission.setRole_id(100);
                role_permission.setPerm_id(111);
                break;

            case 200:
                role_permission.setRole_id(200);
                role_permission.setPerm_id(222);
                break;

            case 300:
                role_permission.setRole_id(300);
                role_permission.setPerm_id(333);
                break;
        }
        role_permissionRepo.save(role_permission);
        return role_permission;

    }
}
