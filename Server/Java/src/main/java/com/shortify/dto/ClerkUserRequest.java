package com.shortify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Clerk User Webhook Request DTO
 * Handles user.created, user.updated, user.deleted events from Clerk
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClerkUserRequest {

    @JsonProperty("instance_id")
    private String instanceId;

    @JsonProperty("object")
    private String object;

    @JsonProperty("type")
    private String type;

    @JsonProperty("data")
    private ClerkUserData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClerkUserData {

        @JsonProperty("id")
        private String id;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        @JsonProperty("image_url")
        private String imageUrl;

        @JsonProperty("email_addresses")
        private List<EmailAddress> emailAddresses;

        // Delete event fields
        @JsonProperty("deleted")
        private Boolean deleted;

        @JsonProperty("object")
        private String object;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailAddress {

        @JsonProperty("email_address")
        private String emailAddress;
    }
}
