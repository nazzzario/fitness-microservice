package com.fitness.userservice.mapper;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UpdateUserRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for User entity and DTOs.
 * Handles conversion between User entity, UserResponse DTO, RegisterRequest DTO, and UpdateUserRequest DTO.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts a User entity to a UserResponse DTO.
     *
     * @param user the User entity to convert
     * @return UserResponse DTO containing user information
     */
    UserResponse toUserResponse(User user);

    /**
     * Converts a RegisterRequest DTO to a User entity.
     * Note: The password field needs to be handled separately (hashed).
     * The role field will default to USER as defined in the User entity.
     * The id, createdAt, and updatedAt fields will be set by the database.
     *
     * @param registerRequest the RegisterRequest DTO to convert
     * @return User entity (without id, createdAt, and updatedAt)
     */
    User toUser(RegisterRequest registerRequest);

    /**
     * Updates an existing User entity with data from a RegisterRequest DTO.
     *
     * @param registerRequest the RegisterRequest DTO containing updated user data
     * @param user the User entity to update
     */
    void updateUserFromRegisterRequest(RegisterRequest registerRequest, @MappingTarget User user);

    /**
     * Updates an existing User entity with data from an UpdateUserRequest DTO.
     *
     * @param updateUserRequest the UpdateUserRequest DTO containing profile update data
     * @param user the User entity to update
     */
    void updateUserFromUpdateUserRequest(UpdateUserRequest updateUserRequest, @MappingTarget User user);
}
