package com.shortify.repository;

import com.shortify.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * User Repository - MongoDB operations for User collection
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find user by Clerk User ID
     */
    Optional<User> findByClerkUserId(String clerkUserId);

    /**
     * Find user by username
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by Clerk User ID that is not deleted
     */
    @Query("{ 'clerk_user_id': ?0, 'is_deleted': { $ne: true } }")
    Optional<User> findActiveByClerkUserId(String clerkUserId);

    /**
     * Find user by username that is not deleted
     */
    @Query("{ 'username': ?0, 'is_deleted': { $ne: true } }")
    Optional<User> findActiveByUsername(String username);

    /**
     * Find all users that are not deleted, ordered by created_at descending
     */
    @Query(value = "{ 'is_deleted': { $ne: true } }", sort = "{ 'created_at': -1 }")
    List<User> findAllActiveUsers();

    /**
     * Find user with highest code (for generating new user code)
     */
    Optional<User> findTopByOrderByCodeDesc();

    /**
     * Check if username exists (excluding deleted users)
     */
    @Query(value = "{ 'username': ?0, 'is_deleted': { $ne: true } }", exists = true)
    boolean existsByUsernameActive(String username);
}
