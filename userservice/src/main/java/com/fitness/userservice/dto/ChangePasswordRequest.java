package com.fitness.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for changing user password.
 */
@Schema(description = "Request DTO for changing user password")
public record ChangePasswordRequest(
        @NotBlank(message = "Current password is required")
        @Schema(description = "Current user password", example = "oldPassword123")
        String currentPassword,

        @NotBlank(message = "New password is required")
        @Size(min = 6, message = "New password must have at least 6 characters")
        @Schema(description = "New password", example = "newPassword123")
        String newPassword,

        @NotBlank(message = "Password confirmation is required")
        @Size(min = 6, message = "Password confirmation must have at least 6 characters")
        @Schema(description = "Password confirmation", example = "newPassword123")
        String confirmPassword) {
}
