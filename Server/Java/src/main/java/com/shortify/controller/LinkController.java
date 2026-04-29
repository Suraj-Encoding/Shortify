package com.shortify.controller;

import com.shortify.dto.AppResponse;
import com.shortify.dto.LinkRequest;
import com.shortify.exception.AppException;
import com.shortify.model.Link;
import com.shortify.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Link Controller - API endpoints for Link operations
 * Base path: /api/v1/link
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/link")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    /**
     * Create Link
     * POST /api/v1/link/
     */
    @PostMapping("/")
    public ResponseEntity<AppResponse<String>> createLink(
            @RequestParam("clerk_user_id") String clerkUserId,
            @RequestBody LinkRequest request) {

        if (clerkUserId == null || clerkUserId.trim().isEmpty()) {
            throw new AppException("Empty clerk user ID provided", HttpStatus.BAD_REQUEST);
        }

        if (request.getData() == null) {
            throw new AppException("Empty link data provided", HttpStatus.BAD_REQUEST);
        }

        LinkRequest.LinkData linkData = request.getData();

        if (linkData.getDestinationUrl() == null || linkData.getDestinationUrl().trim().isEmpty()) {
            throw new AppException("Link destination URL cannot be empty", HttpStatus.BAD_REQUEST);
        }

        if (linkData.getSlug() == null || linkData.getSlug().trim().isEmpty()) {
            throw new AppException("Link slug cannot be empty", HttpStatus.BAD_REQUEST);
        }

        String result = linkService.createLink(clerkUserId.trim(), linkData);
        return ResponseEntity.ok(AppResponse.success(result));
    }

    /**
     * Update Link
     * PUT /api/v1/link/
     */
    @PutMapping("/")
    public ResponseEntity<AppResponse<String>> updateLink(
            @RequestParam("clerk_user_id") String clerkUserId,
            @RequestParam("link_id") String linkId,
            @RequestBody LinkRequest request) {

        if (clerkUserId == null || clerkUserId.trim().isEmpty()) {
            throw new AppException("Empty clerk user ID provided", HttpStatus.BAD_REQUEST);
        }

        if (linkId == null || linkId.trim().isEmpty()) {
            throw new AppException("Empty link ID provided", HttpStatus.BAD_REQUEST);
        }

        if (request.getData() == null) {
            throw new AppException("Empty link data provided", HttpStatus.BAD_REQUEST);
        }

        LinkRequest.LinkData linkData = request.getData();

        if (linkData.getDestinationUrl() == null || linkData.getDestinationUrl().trim().isEmpty()) {
            throw new AppException("Link destination URL cannot be empty", HttpStatus.BAD_REQUEST);
        }

        if (linkData.getSlug() == null || linkData.getSlug().trim().isEmpty()) {
            throw new AppException("Link slug cannot be empty", HttpStatus.BAD_REQUEST);
        }

        String result = linkService.updateLink(clerkUserId.trim(), linkId.trim(), linkData);
        return ResponseEntity.ok(AppResponse.success(result));
    }

    /**
     * Delete Link
     * DELETE /api/v1/link/
     */
    @DeleteMapping("/")
    public ResponseEntity<AppResponse<String>> deleteLink(
            @RequestParam("link_id") String linkId) {

        if (linkId == null || linkId.trim().isEmpty()) {
            throw new AppException("Empty link ID provided", HttpStatus.BAD_REQUEST);
        }

        String result = linkService.deleteLink(linkId.trim());
        return ResponseEntity.ok(AppResponse.success(result));
    }

    /**
     * Get Link
     * GET /api/v1/link/
     */
    @GetMapping("/")
    public ResponseEntity<AppResponse<Link>> getLink(
            @RequestParam("link_id") String linkId) {

        if (linkId == null || linkId.trim().isEmpty()) {
            throw new AppException("Empty link ID provided", HttpStatus.BAD_REQUEST);
        }

        Link link = linkService.getLink(linkId.trim());
        return ResponseEntity.ok(AppResponse.success(link));
    }

    /**
     * Get Links List
     * GET /api/v1/link/list
     */
    @GetMapping("/list")
    public ResponseEntity<AppResponse<List<Link>>> getLinks(
            @RequestParam("clerk_user_id") String clerkUserId) {

        if (clerkUserId == null || clerkUserId.trim().isEmpty()) {
            throw new AppException("Empty clerk user ID provided", HttpStatus.BAD_REQUEST);
        }

        List<Link> links = linkService.getLinks(clerkUserId.trim());
        return ResponseEntity.ok(AppResponse.success(links));
    }
}
