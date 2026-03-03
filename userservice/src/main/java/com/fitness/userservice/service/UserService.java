package com.fitness.userservice.service;

import com.fitness.userservice.dto.ChangePasswordRequest;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UpdateUserRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.UserRole;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Service interface for user-related operations.
 */
public interface UserService {

    /**
     * Retrieves a user profile by ID.
     *
     * @param userId the user ID
     * @return UserResponse containing user information
     */
    UserResponse getUserProfile(String userId);

    /**
     * Registers a new user with email and password.
     *
     * @param request the registration request
     * @return UserResponse of the created user
     */
    UserResponse register(@Valid RegisterRequest request);

    /**
     * Updates user profile information (firstname, lastname).
     *
     * @param userId the user ID
     * @param request the update request
     * @return UserResponse with updated information
     */
    UserResponse updateUserProfile(String userId, @Valid UpdateUserRequest request);

    /**
     * Changes the user's password.
     *
     * @param userId the user ID
     * @param request the change password request
     */
    void changePassword(String userId, @Valid ChangePasswordRequest request);

    /**
     * Deletes a user account.
     *
     * @param userId the user ID
     */
    void deleteUser(String userId);

    /**
     * Retrieves a user by email address.
     *
     * @param email the email address
     * @return UserResponse if user is found
     */
    UserResponse getUserByEmail(String email);

    /**
     * Checks if a user exists by ID.
     *
     * @param userId the user ID
     * @return true if user exists, false otherwise
     */
    boolean existsById(String userId);

    /**
     * Retrieves all users (typically for admin purposes).
     *
     * @return List of UserResponse for all users
     */
    List<UserResponse> getAllUsers();

    /**
     * Updates the role of a user.
     *
     * @param userId the user ID
     * @param role the new role
     * @return UserResponse with updated role
     */
    UserResponse updateUserRole(String userId, UserRole role);
}
