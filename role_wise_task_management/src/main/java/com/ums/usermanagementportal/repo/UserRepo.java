package com.ums.usermanagementportal.repo;

import com.ums.usermanagementportal.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<user,Integer> {

    @Query("SELECT u.role FROM user u WHERE u.id = :id")
    String findRoleById(@Param("id") Integer id);

    @Query("SELECT u.email FROM user u WHERE u.role IN :roles")
    List<String> findEmailByRole(@Param("roles") String... roles);

}


