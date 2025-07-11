package com.ums.usermanagementportal.service;

import com.ums.usermanagementportal.entity.user_role;
import com.ums.usermanagementportal.repo.User_RoleRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User_RoleService {

    @Autowired
    private User_RoleRepo user_roleRepo;

    public user_role assignValue(Integer id, Integer rid){
        user_role userRole= new user_role();
        userRole.setId(id);
        userRole.setRole_id(rid);

        user_roleRepo.save(userRole);

        return userRole;
    }
}
