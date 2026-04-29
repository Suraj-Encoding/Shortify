package com.shortify.controller;

import com.shortify.dto.AppResponse;
import com.shortify.dto.ClerkUserRequest;
import com.shortify.exception.AppException;
import com.shortify.model.User;
import com.shortify.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User Controller - API endpoints for User operations
 * Base path: /api/v1/user
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Handle Clerk User Webhook
     * POST /api/v1/user/webhook
     */
    @PostMapping("/webhook")
    public ResponseEntity<AppResponse<String>> handleClerkUserWebhook(
            @RequestBody ClerkUserRequest request) {
        
        log.info("Received Clerk webhook: type={}", request.getType());

        if (request.getData() == null) {
            throw new AppException("Empty clerk user data provided", HttpStatus.BAD_REQUEST);
        }

        ClerkUserRequest.ClerkUserData clerkUser = request.getData();

        // Validate based on event type
        if ("user.deleted".equals(request.getType())) {
            if (!Boolean.TRUE.equals(clerkUser.getDeleted()) || !"user".equals(clerkUser.getObject())) {
                throw new AppException("Invalid delete user request found", HttpStatus.BAD_REQUEST);
            }
        } else {
            if (clerkUser.getFirstName() == null || clerkUser.getFirstName().trim().isEmpty()) {
                throw new AppException("User first name cannot be empty", HttpStatus.BAD_REQUEST);
            }
            if (clerkUser.getLastName() == null || clerkUser.getLastName().trim().isEmpty()) {
                throw new AppException("User last name cannot be empty", HttpStatus.BAD_REQUEST);
            }
            if (clerkUser.getEmailAddresses() == null || 
                clerkUser.getEmailAddresses().isEmpty() ||
                clerkUser.getEmailAddresses().get(0).getEmailAddress() == null ||
                clerkUser.getEmailAddresses().get(0).getEmailAddress().trim().isEmpty()) {
                throw new AppException("User email address cannot be empty", HttpStatus.BAD_REQUEST);
            }
        }

        String result;
        switch (request.getType()) {
            case "user.created":
                result = userService.createUser(clerkUser);
                break;
            case "user.updated":
                result = userService.updateUser(clerkUser);
                break;
            case "user.deleted":
                result = userService.deleteUser(clerkUser);
                break;
            default:
                throw new AppException(
                        String.format("Invalid clerk user webhook type '%s' provided", request.getType()),
                        HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(AppResponse.success(result));
    }

    /**
     * Update Username
     * PUT /api/v1/user/username
     */
    @PutMapping("/username")
    public ResponseEntity<AppResponse<String>> updateUsername(
            @RequestParam("clerk_user_id") String clerkUserId,
            @RequestParam("username") String username) {

        if (clerkUserId == null || clerkUserId.trim().isEmpty()) {
            throw new AppException("Empty clerk user ID provided", HttpStatus.BAD_REQUEST);
        }

        if (username == null || username.trim().isEmpty()) {
            throw new AppException("Empty username provided", HttpStatus.BAD_REQUEST);
        }

        String result = userService.updateUsername(clerkUserId.trim(), username.trim().toLowerCase());
        return ResponseEntity.ok(AppResponse.success(result));
    }

    /**
     * Get User
     * GET /api/v1/user/
     */
    @GetMapping("/")
    public ResponseEntity<AppResponse<User>> getUser(
            @RequestParam("clerk_user_id") String clerkUserId) {

        if (clerkUserId == null || clerkUserId.trim().isEmpty()) {
            throw new AppException("Empty clerk user ID provided", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUser(clerkUserId.trim());
        return ResponseEntity.ok(AppResponse.success(user));
    }

    /**
     * Get Users List
     * GET /api/v1/user/list
     */
    @GetMapping("/list")
    public ResponseEntity<AppResponse<List<User>>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(AppResponse.success(users));
    }
}
