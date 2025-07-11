package com.ums.usermanagementportal.repo;

import com.ums.usermanagementportal.entity.permissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionsRepo extends JpaRepository<permissions,Integer> {
    @Query("SELECT p FROM permissions p " +
            "JOIN role_permission rp ON p.perm_id = rp.perm_id " +
            "JOIN user_role ur ON rp.role_id = ur.role_id " +
            "JOIN user u ON ur.id = u.id " +
            "WHERE u.id = :userId")
    List<permissions> findPermissionsByUserId(@Param("userId") Integer userId);
}
