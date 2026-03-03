package com.fitness.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request DTO for user registration")
public record RegisterRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Schema(description = "User email address", example = "user@example.com")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must have at least of 6 characters")
        @Schema(description = "User password", example = "password123")
        String password,

        @Schema(description = "User first name", example = "John")
        String firstname,

        @Schema(description = "User last name", example = "Doe")
        String lastname) {
}
