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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, userMapper, passwordEncoder);
    }

    private User makeUser() {
        User user = new User();
        user.setId("123");
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setRole(UserRole.USER);
        return user;
    }

    @Test
    void getUserProfile_found() {
        User user = makeUser();
        when(userRepository.findById("123")).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserProfile("123");

        assertEquals("123", response.id());
        assertEquals("test@example.com", response.email());
        verify(userRepository).findById("123");
    }

    @Test
    void getUserProfile_notFound() {
        when(userRepository.findById("123")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserProfile("123"));
    }

    @Test
    void register_success() {
        RegisterRequest req = new RegisterRequest("new@example.com", "pass123", "Jane", "Doe");
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(i -> {
            User u = i.getArgument(0);
            u.setId("abc");
            // simulate hibernate creation timestamp
            u.setCreatedAt(LocalDateTime.now());
            return u;
        });

        UserResponse resp = userService.register(req);
        assertEquals("abc", resp.id());
        assertEquals("new@example.com", resp.email());
        assertNotNull(resp.createdAt());
    }

    @Test
    void register_duplicateEmail() {
        when(userRepository.existsByEmail("dup@example.com")).thenReturn(true);
        RegisterRequest req = new RegisterRequest("dup@example.com", "pass123", null, null);
        assertThrows(EmailAlreadyExistsException.class, () -> userService.register(req));
    }

    @Test
    void updateUserProfile_success() {
        User user = makeUser();
        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UpdateUserRequest req = new UpdateUserRequest("Alice", "Smith");
        UserResponse resp = userService.updateUserProfile("123", req);
        assertEquals("Alice", resp.firstname());
        assertEquals("Smith", resp.lastname());
    }

    @Test
    void updateUserProfile_notFound() {
        when(userRepository.findById("123")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUserProfile("123", new UpdateUserRequest("a","b")));
    }

    @Test
    void changePassword_success() {
        User user = makeUser();
        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        ChangePasswordRequest req = new ChangePasswordRequest("password", "newpass", "newpass");
        userService.changePassword("123", req);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void changePassword_wrongCurrent() {
        User user = makeUser();
        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        ChangePasswordRequest req = new ChangePasswordRequest("bad", "newpass", "newpass");
        assertThrows(InvalidPasswordException.class, () -> userService.changePassword("123", req));
    }

    @Test
    void changePassword_mismatch() {
        User user = makeUser();
        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        ChangePasswordRequest req = new ChangePasswordRequest("password", "newpass", "other");
        assertThrows(InvalidPasswordException.class, () -> userService.changePassword("123", req));
    }

    @Test
    void deleteUser_success() {
        when(userRepository.existsById("123")).thenReturn(true);
        userService.deleteUser("123");
        verify(userRepository).deleteById("123");
    }

    @Test
    void deleteUser_notFound() {
        when(userRepository.existsById("123")).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser("123"));
    }

    @Test
    void getUserByEmail_success() {
        User user = makeUser();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        UserResponse resp = userService.getUserByEmail("test@example.com");
        assertEquals("test@example.com", resp.email());
    }

    @Test
    void getUserByEmail_notFound() {
        when(userRepository.findByEmail("nope@example.com")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail("nope@example.com"));
    }

    @Test
    void existsById_true() {
        when(userRepository.existsById("123")).thenReturn(true);
        assertTrue(userService.existsById("123"));
    }

    @Test
    void existsById_false() {
        when(userRepository.existsById("123")).thenReturn(false);
        assertFalse(userService.existsById("123"));
    }

    @Test
    void getAllUsers() {
        User user1 = makeUser();
        User user2 = makeUser();
        user2.setId("456");
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        List<UserResponse> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void updateUserRole_success() {
        User user = makeUser();
        when(userRepository.findById("123")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        UserResponse resp = userService.updateUserRole("123", UserRole.ADMIN);
        assertEquals(UserRole.ADMIN, resp.role());
    }

}
