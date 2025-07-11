package com.ums.usermanagementportal.repo;

import com.ums.usermanagementportal.entity.roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<roles,Integer> {

    @Query("SELECT r.role_id FROM roles r WHERE r.roles = :role")
    Integer findIdByRole(@Param("role") String role);
}
