package com.ums.usermanagementportal.repo;

import com.ums.usermanagementportal.entity.user_role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User_RoleRepo extends JpaRepository<user_role,Integer> {
}
