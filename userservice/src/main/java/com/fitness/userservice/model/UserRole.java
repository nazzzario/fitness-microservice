package com.fitness.userservice.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User roles in the system.
 */
@Schema(description = "User role enumeration", example = "USER")
public enum UserRole {
    USER, ADMIN
}
