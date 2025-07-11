package com.ums.usermanagementportal.repo;

import com.ums.usermanagementportal.entity.role_permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Role_PermissionRepo extends JpaRepository<role_permission,Integer> {
}
