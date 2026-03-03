package com.fitness.userservice.controller;

import com.fitness.userservice.dto.ChangePasswordRequest;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UpdateUserRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.UserRole;
import com.fitness.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for user-related endpoints.
 * Handles user registration, profile management, and user operations.
 */
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(name = "User Management", description = "APIs for user registration, profile management, and user operations")
public class UserController {

    private UserService userService;

    /**
     * Retrieves a user profile by user ID.
     *
     * @param userId the user ID
     * @return UserResponse with user information
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get user profile", description = "Retrieves a user profile by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    public ResponseEntity<UserResponse> getUserProfile(
            @Parameter(description = "User ID") @PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    /**
     * Registers a new user.
     *
     * @param request the registration request
     * @return UserResponse of the created user
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid registration data or email already exists"),
            @ApiResponse(responseCode = "422", description = "Validation error")
    })
    public ResponseEntity<UserResponse> register(
            @RequestBody @Valid RegisterRequest request) {
        return new ResponseEntity<>(userService.register(request), HttpStatus.CREATED);
    }

    /**
     * Updates user profile information.
     *
     * @param userId the user ID
     * @param request the update request
     * @return UserResponse with updated information
     */
    @PutMapping("/{userId}")
    @Operation(summary = "Update user profile", description = "Updates user's firstname and lastname")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile updated successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid update data")
    })
    public ResponseEntity<UserResponse> updateUserProfile(
            @Parameter(description = "User ID") @PathVariable String userId,
            @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUserProfile(userId, request));
    }

    /**
     * Changes the user's password.
     *
     * @param userId the user ID
     * @param request the change password request
     * @return Success response
     */
    @PostMapping("/{userId}/change-password")
    @Operation(summary = "Change user password", description = "Changes the user's password after verifying the current password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password changed successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid current password or password mismatch")
    })
    public ResponseEntity<Void> changePassword(
            @Parameter(description = "User ID") @PathVariable String userId,
            @RequestBody @Valid ChangePasswordRequest request) {
        userService.changePassword(userId, request);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a user account.
     *
     * @param userId the user ID
     * @return Success response
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user account", description = "Deletes a user account by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID") @PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a user by email address.
     *
     * @param email the email address
     * @return UserResponse with user information
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Retrieves a user by email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> getUserByEmail(
            @Parameter(description = "Email address") @PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    /**
     * Checks if a user exists by ID.
     *
     * @param userId the user ID
     * @return Boolean response
     */
    @GetMapping("/{userId}/exists")
    @Operation(summary = "Check if user exists", description = "Checks if a user exists by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Result returned successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))})
    })
    public ResponseEntity<Boolean> existsById(
            @Parameter(description = "User ID") @PathVariable String userId) {
        return ResponseEntity.ok(userService.existsById(userId));
    }

    /**
     * Retrieves all users (admin operation).
     *
     * @return List of all users
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all users from the system (admin operation)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))})
    })
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Updates the role of a user (admin operation).
     *
     * @param userId the user ID
     * @param role the new role
     * @return UserResponse with updated role
     */
    @PatchMapping("/{userId}/role")
    @Operation(summary = "Update user role", description = "Updates the role of a user (admin operation)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role updated successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> updateUserRole(
            @Parameter(description = "User ID") @PathVariable String userId,
            @Parameter(description = "New user role") @RequestParam UserRole role) {
        return ResponseEntity.ok(userService.updateUserRole(userId, role));
    }
}
