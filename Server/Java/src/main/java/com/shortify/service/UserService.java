package com.shortify.service;

import com.shortify.dto.ClerkUserRequest;
import com.shortify.exception.AppException;
import com.shortify.model.User;
import com.shortify.repository.LinkRepository;
import com.shortify.repository.UserRepository;
import com.shortify.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User Service - Business logic for User operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LinkRepository linkRepository;
    private final MongoTemplate mongoTemplate;

    /**
     * Generate unique user code
     */
    public Long generateUserCode() {
        return userRepository.findTopByOrderByCodeDesc()
                .map(user -> user.getCode() + 1)
                .orElse(1L);
    }

    /**
     * Create new user from Clerk webhook
     */
    public String createUser(ClerkUserRequest.ClerkUserData clerkUser) {
        log.info("Creating user with Clerk ID: {}", clerkUser.getId());

        LocalDateTime now = TimeUtils.getCurrentTimeInIST();
        String actionUser = "clerk_webhook";

        Long userCode = generateUserCode();
        String username = clerkUser.getFirstName().toLowerCase() + 
                          clerkUser.getLastName().toLowerCase() + 
                          userCode;

        String email = clerkUser.getEmailAddresses() != null && 
                       !clerkUser.getEmailAddresses().isEmpty() ?
                       clerkUser.getEmailAddresses().get(0).getEmailAddress() : null;

        User user = User.builder()
                .clerkUserId(clerkUser.getId().trim())
                .code(userCode)
                .firstName(clerkUser.getFirstName())
                .lastName(clerkUser.getLastName())
                .email(email)
                .username(username)
                .createdAt(now)
                .createdBy(getActionUser(actionUser))
                .build();

        userRepository.save(user);
        log.info("User created successfully with ID: {}", user.getId());

        return "User created successfully!";
    }

    /**
     * Update user from Clerk webhook
     */
    public String updateUser(ClerkUserRequest.ClerkUserData clerkUser) {
        log.info("Updating user with Clerk ID: {}", clerkUser.getId());

        User existingUser = userRepository.findByClerkUserId(clerkUser.getId().trim())
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        LocalDateTime now = TimeUtils.getCurrentTimeInIST();
        String actionUser = "clerk_webhook";

        Query query = new Query(Criteria.where("clerk_user_id").is(clerkUser.getId().trim()));
        Update update = new Update();

        if (clerkUser.getFirstName() != null) {
            if (!clerkUser.getFirstName().trim().isEmpty()) {
                update.set("first_name", clerkUser.getFirstName());
            } else {
                update.unset("first_name");
            }
        }

        if (clerkUser.getLastName() != null) {
            if (!clerkUser.getLastName().trim().isEmpty()) {
                update.set("last_name", clerkUser.getLastName());
            } else {
                update.unset("last_name");
            }
        }

        if (clerkUser.getEmailAddresses() != null && !clerkUser.getEmailAddresses().isEmpty()) {
            String email = clerkUser.getEmailAddresses().get(0).getEmailAddress();
            if (email != null && !email.trim().isEmpty()) {
                update.set("email", email);
            } else {
                update.unset("email");
            }
        }

        if (clerkUser.getImageUrl() != null) {
            if (!clerkUser.getImageUrl().trim().isEmpty()) {
                update.set("image_url", clerkUser.getImageUrl());
            } else {
                update.unset("image_url");
            }
        }

        update.set("updated_at", now);
        update.set("updated_by", getActionUser(actionUser));

        var result = mongoTemplate.updateFirst(query, update, User.class);

        if (result.getMatchedCount() == 0) {
            throw new AppException("User not found", HttpStatus.NOT_FOUND);
        }

        log.info("User updated successfully");
        return "User updated successfully!";
    }

    /**
     * Delete user (soft delete) from Clerk webhook
     */
    public String deleteUser(ClerkUserRequest.ClerkUserData clerkUser) {
        log.info("Deleting user with Clerk ID: {}", clerkUser.getId());

        LocalDateTime now = TimeUtils.getCurrentTimeInIST();
        String actionUser = "clerk_webhook";

        // Mark user as deleted
        Query userQuery = new Query(Criteria.where("clerk_user_id").is(clerkUser.getId().trim()));
        Update userUpdate = new Update()
                .set("is_deleted", true)
                .set("deleted_at", now)
                .set("deleted_by", getActionUser(actionUser));

        var userResult = mongoTemplate.updateFirst(userQuery, userUpdate, User.class);

        if (userResult.getMatchedCount() == 0) {
            throw new AppException("User not found", HttpStatus.NOT_FOUND);
        }

        // Mark all user's links as user deleted
        Query linkQuery = new Query(Criteria.where("clerk_user_id").is(clerkUser.getId().trim()));
        Update linkUpdate = new Update()
                .set("is_user_deleted", true)
                .set("updated_at", now)
                .set("updated_by", getActionUser(actionUser));

        mongoTemplate.updateMulti(linkQuery, linkUpdate, "link");

        log.info("User deleted successfully");
        return "User deleted successfully!";
    }

    /**
     * Update username
     */
    public String updateUsername(String clerkUserId, String username) {
        log.info("Updating username for Clerk ID: {} to: {}", clerkUserId, username);

        // Check if username is already taken
        userRepository.findActiveByUsername(username).ifPresent(existingUser -> {
            if (existingUser.getClerkUserId().equals(clerkUserId)) {
                throw new AppException("This is already your current username. Please choose a new username.", 
                        HttpStatus.BAD_REQUEST);
            } else {
                throw new AppException("Username already taken by another user. Please choose a different username.", 
                        HttpStatus.BAD_REQUEST);
            }
        });

        LocalDateTime now = TimeUtils.getCurrentTimeInIST();

        Query query = new Query(Criteria.where("clerk_user_id").is(clerkUserId));
        Update update = new Update()
                .set("username", username)
                .set("updated_at", now)
                .set("updated_by", getActionUser(clerkUserId));

        var result = mongoTemplate.updateFirst(query, update, User.class);

        if (result.getMatchedCount() == 0) {
            throw new AppException("User not found", HttpStatus.NOT_FOUND);
        }

        if (result.getModifiedCount() == 0) {
            throw new AppException("Username not updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Username updated successfully");
        return "Username updated successfully!";
    }

    /**
     * Get user by Clerk User ID
     */
    public User getUser(String clerkUserId) {
        log.info("Getting user with Clerk ID: {}", clerkUserId);

        User user = userRepository.findByClerkUserId(clerkUserId)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new AppException("User is already deleted", HttpStatus.NOT_FOUND);
        }

        return user;
    }

    /**
     * Get user by username (active only)
     */
    public User getUserByUsername(String username) {
        log.info("Getting user with username: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        if (Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new AppException("User is already deleted", HttpStatus.NOT_FOUND);
        }

        return user;
    }

    /**
     * Get all users (active only)
     */
    public List<User> getUsers() {
        log.info("Getting all active users");
        return userRepository.findAllActiveUsers();
    }

    /**
     * Get action user string
     */
    private String getActionUser(String user) {
        return (user != null && !user.isEmpty()) ? user : "system";
    }
}
