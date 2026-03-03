package com.fitness.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for updating user profile information.
 */
@Schema(description = "Request DTO for updating user profile")
public record UpdateUserRequest(
        @Schema(description = "User first name", example = "John")
        String firstname,

        @Schema(description = "User last name", example = "Doe")
        String lastname) {
}
