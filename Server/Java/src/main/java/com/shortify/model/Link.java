package com.shortify.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * Link Entity - Maps to 'link' collection in MongoDB
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "link")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Link {

    @Id
    @JsonProperty("_id")
    private String id;

    @Field("user_id")
    @JsonProperty("user_id")
    private String userId;

    @Field("clerk_user_id")
    @JsonProperty("clerk_user_id")
    private String clerkUserId;

    @Field("is_user_deleted")
    @JsonProperty("is_user_deleted")
    private Boolean isUserDeleted;

    @Field("title")
    @JsonProperty("title")
    private String title;

    @Field("description")
    @JsonProperty("description")
    private String description;

    @Field("destination_url")
    @JsonProperty("destination_url")
    private String destinationUrl;

    @Field("slug")
    @JsonProperty("slug")
    private String slug;

    @Field("created_at")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Field("created_by")
    @JsonProperty("created_by")
    private String createdBy;

    @Field("updated_at")
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @Field("updated_by")
    @JsonProperty("updated_by")
    private String updatedBy;
}
