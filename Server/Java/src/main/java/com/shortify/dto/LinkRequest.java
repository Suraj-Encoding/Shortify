package com.shortify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Link Request DTO
 * Used for create/update link operations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkRequest {

    @JsonProperty("data")
    private LinkData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LinkData {

        @JsonProperty("title")
        private String title;

        @JsonProperty("description")
        private String description;

        @JsonProperty("destination_url")
        private String destinationUrl;

        @JsonProperty("slug")
        private String slug;
    }
}
