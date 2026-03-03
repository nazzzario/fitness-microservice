package com.fitness.userservice.dto;

import com.fitness.userservice.model.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response DTO containing user information")
public record UserResponse(
        @Schema(description = "Unique user identifier", example = "123e4567-e89b-12d3-a456-426614174000")
        String id,

        @Schema(description = "User email address", example = "user@example.com")
        String email,

        @Schema(description = "User first name", example = "John")
        String firstname,

        @Schema(description = "User last name", example = "Doe")
        String lastname,

        @Schema(description = "User role", example = "USER")
        UserRole role,

        @Schema(description = "Account creation timestamp")
        LocalDateTime createdAt,

        @Schema(description = "Last update timestamp")
        LocalDateTime updatedAt) {
}