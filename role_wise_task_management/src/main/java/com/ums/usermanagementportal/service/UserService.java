package com.ums.usermanagementportal.service;

import com.ums.usermanagementportal.repo.UserRepo;
import com.ums.usermanagementportal.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepo userRepo;

    public user getUserById(int id) {
        try {
            logger.info("Fetching user with ID: {}", id);
            return userRepo.findById(id).orElse(null);
        } catch (Exception e) {
            logger.error("Error fetching user with ID: {}", id, e);
            throw e; // Re-throwing the exception for higher-level handling
        }
    }

    public List<String> getEmailsByRoles(String... roles){
        return userRepo.findEmailByRole(roles);
    }

    public List<user> getAllUsers() {
        try {
            logger.info("Fetching all users");
            return userRepo.findAll();
        } catch (Exception e) {
            logger.error("Error fetching all users", e);
            throw e; // Re-throwing the exception for higher-level handling
        }
    }

    public user saveOrUpdateUser(user user) {
        try {
            logger.info("Saving or updating user: {}", user);
            user savedUser = userRepo.save(user);
            logger.info("User saved/updated: {}", savedUser);
            return savedUser;
        } catch (Exception e) {
            logger.error("Error saving or updating user: {}", user, e);
            throw e; // Re-throwing the exception for higher-level handling
        }
    }

    public String checkId(Integer id) {
        try {
            logger.info("Checking role for user ID: {}", id);
            return userRepo.findRoleById(id);
        } catch (Exception e) {
            logger.error("Error checking role for user ID: {}", id, e);
            throw e; // Re-throwing the exception for higher-level handling
        }
    }

    public void deleteUser(int id) {
        try {
            logger.info("Deleting user with ID: {}", id);
            userRepo.deleteById(id);
            logger.info("User with ID: {} deleted", id);
        } catch (Exception e) {
            logger.error("Error deleting user with ID: {}", id, e);
            throw e; // Re-throwing the exception for higher-level handling
        }
    }

}
