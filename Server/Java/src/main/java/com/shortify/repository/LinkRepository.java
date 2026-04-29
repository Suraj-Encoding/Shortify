package com.shortify.repository;

import com.shortify.model.Link;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Link Repository - MongoDB operations for Link collection
 */
@Repository
public interface LinkRepository extends MongoRepository<Link, String> {

    /**
     * Find link by Clerk User ID and Slug
     */
    Optional<Link> findByClerkUserIdAndSlug(String clerkUserId, String slug);

    /**
     * Find all links by Clerk User ID that are not deleted, ordered by created_at descending
     */
    @Query(value = "{ 'clerk_user_id': ?0, 'is_user_deleted': { $ne: true } }", sort = "{ 'created_at': -1 }")
    List<Link> findAllActiveByClerkUserId(String clerkUserId);

    /**
     * Find link by ID where user is not deleted
     */
    @Query("{ '_id': ?0, 'is_user_deleted': { $ne: true } }")
    Optional<Link> findActiveById(String id);

    /**
     * Find link by Clerk User ID and Slug where user is not deleted
     */
    @Query("{ 'clerk_user_id': ?0, 'slug': ?1, 'is_user_deleted': { $ne: true } }")
    Optional<Link> findActiveByClerkUserIdAndSlug(String clerkUserId, String slug);

    /**
     * Check if slug exists for user
     */
    boolean existsByClerkUserIdAndSlug(String clerkUserId, String slug);

    /**
     * Find all links by Clerk User ID (including deleted)
     */
    List<Link> findByClerkUserId(String clerkUserId);
}
