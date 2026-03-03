package com.fitness.userservice.service.impl;

import com.fitness.userservice.dto.ChangePasswordRequest;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UpdateUserRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.exception.EmailAlreadyExistsException;
import com.fitness.userservice.exception.InvalidPasswordException;
import com.fitness.userservice.exception.UserNotFoundException;
import com.fitness.userservice.mapper.UserMapper;
import com.fitness.userservice.model.User;
import com.fitness.userservice.model.UserRole;
import com.fitness.userservice.repository.UserRepository;
import com.fitness.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of UserService interface.
 * Handles user registration, profile management, and authentication-related operations.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves a user profile by user ID.
     *
     * @param userId the ID of the user to retrieve
     * @return UserResponse DTO containing user information
     * @throws UserNotFoundException if user is not found
     */
    @Override
    public UserResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return userMapper.toUserResponse(user);
    }

    /**
     * Registers a new user with email and password.
     * Encodes the password before saving to the database.
     *
     * @param request RegisterRequest DTO containing user registration data
     * @return UserResponse DTO containing the created user information
     * @throws EmailAlreadyExistsException if email is already registered
     */
    @Override
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("User with email: " + request.email() + " is already registered");
        }

        // Convert RegisterRequest to User entity
        User user = userMapper.toUser(request);

        // Encode and set the password
        user.setPassword(passwordEncoder.encode(request.password()));

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Convert the saved User entity to UserResponse DTO
        return userMapper.toUserResponse(savedUser);
    }

    /**
     * Updates user profile information (firstname, lastname).
     *
     * @param userId the user ID
     * @param request the update request containing new profile data
     * @return UserResponse with updated information
     * @throws UserNotFoundException if user is not found
     */
    @Override
    public UserResponse updateUserProfile(String userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Update the user's firstname and lastname
        if (request.firstname() != null && !request.firstname().isBlank()) {
            user.setFirstname(request.firstname());
        }
        if (request.lastname() != null && !request.lastname().isBlank()) {
            user.setLastname(request.lastname());
        }

        // Save the updated user
        User updatedUser = userRepository.save(user);

        return userMapper.toUserResponse(updatedUser);
    }

    /**
     * Changes the user's password.
     * Validates the current password before allowing the change.
     *
     * @param userId the user ID
     * @param request the change password request
     * @throws UserNotFoundException if user is not found
     * @throws InvalidPasswordException if current password is incorrect or new password doesn't match confirmation
     */
    @Override
    public void changePassword(String userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Verify the current password
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        // Check if new password and confirmation match
        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new InvalidPasswordException("New password and confirmation do not match");
        }

        // Check if new password is the same as current password
        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new InvalidPasswordException("New password must be different from the current password");
        }

        // Encode and set the new password
        user.setPassword(passwordEncoder.encode(request.newPassword()));

        // Save the updated user
        userRepository.save(user);
    }

    /**
     * Deletes a user account.
     *
     * @param userId the user ID
     * @throws UserNotFoundException if user is not found
     */
    @Override
    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    /**
     * Retrieves a user by email address.
     *
     * @param email the email address
     * @return UserResponse if user is found
     * @throws UserNotFoundException if user is not found
     */
    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return userMapper.toUserResponse(user);
    }

    /**
     * Checks if a user exists by ID.
     *
     * @param userId the user ID
     * @return true if user exists, false otherwise
     */
    @Override
    public boolean existsById(String userId) {
        return userRepository.existsById(userId);
    }

    /**
     * Retrieves all users (typically for admin purposes).
     *
     * @return List of UserResponse for all users
     */
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    /**
     * Updates the role of a user.
     *
     * @param userId the user ID
     * @param role the new role
     * @return UserResponse with updated role
     * @throws UserNotFoundException if user is not found
     */
    @Override
    public UserResponse updateUserRole(String userId, UserRole role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        user.setRole(role);
        User updatedUser = userRepository.save(user);

        return userMapper.toUserResponse(updatedUser);
    }
}
