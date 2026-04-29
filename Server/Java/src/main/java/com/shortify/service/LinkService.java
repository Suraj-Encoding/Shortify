package com.shortify.service;

import com.shortify.dto.LinkRequest;
import com.shortify.exception.AppException;
import com.shortify.model.Link;
import com.shortify.model.User;
import com.shortify.repository.LinkRepository;
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
 * Link Service - Business logic for Link operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final UserService userService;
    private final MongoTemplate mongoTemplate;

    /**
     * Create new link
     */
    public String createLink(String clerkUserId, LinkRequest.LinkData linkData) {
        log.info("Creating link for Clerk ID: {} with slug: {}", clerkUserId, linkData.getSlug());

        // Check if slug is already assigned to another link
        linkRepository.findByClerkUserIdAndSlug(clerkUserId, linkData.getSlug())
                .ifPresent(existingLink -> {
                    throw new AppException(
                            "The provided slug is already assigned to another link. Please choose a different slug.",
                            HttpStatus.BAD_REQUEST);
                });

        // Get user
        User user = userService.getUser(clerkUserId);

        LocalDateTime now = TimeUtils.getCurrentTimeInIST();

        Link link = Link.builder()
                .userId(user.getId())
                .clerkUserId(user.getClerkUserId())
                .isUserDeleted(false)
                .title(linkData.getTitle())
                .description(linkData.getDescription())
                .destinationUrl(linkData.getDestinationUrl())
                .slug(linkData.getSlug())
                .createdAt(now)
                .createdBy(getActionUser(clerkUserId))
                .build();

        linkRepository.save(link);
        log.info("Link created successfully with ID: {}", link.getId());

        return "Link created successfully!";
    }

    /**
     * Update link
     */
    public String updateLink(String clerkUserId, String linkId, LinkRequest.LinkData linkData) {
        log.info("Updating link ID: {} for Clerk ID: {}", linkId, clerkUserId);

        // Check if slug is already assigned to another link (excluding current link)
        linkRepository.findByClerkUserIdAndSlug(clerkUserId, linkData.getSlug())
                .ifPresent(existingLink -> {
                    if (!existingLink.getId().equals(linkId)) {
                        throw new AppException(
                                "The provided slug is already assigned to another link. Please choose a different slug.",
                                HttpStatus.BAD_REQUEST);
                    }
                });

        LocalDateTime now = TimeUtils.getCurrentTimeInIST();

        Query query = new Query(Criteria.where("_id").is(linkId));
        Update update = new Update();

        if (linkData.getTitle() != null) {
            if (!linkData.getTitle().trim().isEmpty()) {
                update.set("title", linkData.getTitle());
            } else {
                update.unset("title");
            }
        }

        if (linkData.getDescription() != null) {
            if (!linkData.getDescription().trim().isEmpty()) {
                update.set("description", linkData.getDescription());
            } else {
                update.unset("description");
            }
        }

        if (linkData.getDestinationUrl() != null) {
            if (!linkData.getDestinationUrl().trim().isEmpty()) {
                update.set("destination_url", linkData.getDestinationUrl());
            } else {
                update.unset("destination_url");
            }
        }

        if (linkData.getSlug() != null) {
            if (!linkData.getSlug().trim().isEmpty()) {
                update.set("slug", linkData.getSlug());
            } else {
                update.unset("slug");
            }
        }

        update.set("updated_at", now);
        update.set("updated_by", getActionUser(clerkUserId));

        var result = mongoTemplate.updateFirst(query, update, Link.class);

        if (result.getMatchedCount() == 0) {
            throw new AppException("Link not found", HttpStatus.NOT_FOUND);
        }

        if (result.getModifiedCount() == 0) {
            throw new AppException("No link updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Link updated successfully");
        return "Link updated successfully!";
    }

    /**
     * Delete link
     */
    public String deleteLink(String linkId) {
        log.info("Deleting link ID: {}", linkId);

        if (!linkRepository.existsById(linkId)) {
            throw new AppException("Link not found", HttpStatus.NOT_FOUND);
        }

        linkRepository.deleteById(linkId);
        log.info("Link deleted successfully");

        return "Link deleted successfully!";
    }

    /**
     * Get link by ID
     */
    public Link getLink(String linkId) {
        log.info("Getting link with ID: {}", linkId);

        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new AppException("Link not found", HttpStatus.NOT_FOUND));

        if (Boolean.TRUE.equals(link.getIsUserDeleted())) {
            throw new AppException("User associated with the given link is already deleted", HttpStatus.NOT_FOUND);
        }

        return link;
    }

    /**
     * Get all links for a user
     */
    public List<Link> getLinks(String clerkUserId) {
        log.info("Getting all links for Clerk ID: {}", clerkUserId);
        return linkRepository.findAllActiveByClerkUserId(clerkUserId);
    }

    /**
     * Get destination URL for redirect
     */
    public String getDestinationUrl(String username, String slug) {
        log.info("Getting destination URL for username: {} and slug: {}", username, slug);

        // Get user by username
        User user = userService.getUserByUsername(username);

        // Get link by clerk user id and slug
        Link link = linkRepository.findActiveByClerkUserIdAndSlug(user.getClerkUserId(), slug)
                .orElseThrow(() -> new AppException("Link not found", HttpStatus.NOT_FOUND));

        return link.getDestinationUrl();
    }

    /**
     * Get action user string
     */
    private String getActionUser(String user) {
        return (user != null && !user.isEmpty()) ? user : "system";
    }
}
