package com.shortify.controller;

import com.shortify.service.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Redirect Controller - Handles URL redirect
 * Route: /{username}/{slug}
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final LinkService linkService;

    /**
     * Redirect URL
     * GET /{username}/{slug}
     */
    @GetMapping("/{username}/{slug}")
    public void redirectUrl(
            @PathVariable String username,
            @PathVariable String slug,
            HttpServletResponse response) throws IOException {
        
        log.info("🔗 Link URL Path: {}/{}", username, slug);

        String destinationUrl = linkService.getDestinationUrl(username.trim(), slug.trim());
        
        response.sendRedirect(destinationUrl);
    }
}
