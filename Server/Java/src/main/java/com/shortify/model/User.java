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
 * User Entity - Maps to 'user' collection in MongoDB
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @JsonProperty("_id")
    private String id;

    @Field("clerk_user_id")
    @JsonProperty("clerk_user_id")
    private String clerkUserId;

    @Field("code")
    @JsonProperty("code")
    private Long code;

    @Field("first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Field("last_name")
    @JsonProperty("last_name")
    private String lastName;

    @Field("email")
    @JsonProperty("email")
    private String email;

    @Field("username")
    @JsonProperty("username")
    private String username;

    @Field("image_url")
    @JsonProperty("image_url")
    private String imageUrl;

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

    @Field("is_deleted")
    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    @Field("deleted_at")
    @JsonProperty("deleted_at")
    private LocalDateTime deletedAt;

    @Field("deleted_by")
    @JsonProperty("deleted_by")
    private String deletedBy;
}
