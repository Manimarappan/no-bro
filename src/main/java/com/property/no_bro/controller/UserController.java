package com.property.no_bro.controller;

import com.property.no_bro.dto.request.UserRequest;
import com.property.no_bro.dto.response.UserResponse;
import com.property.no_bro.dto.ApiResponse;
import com.property.no_bro.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse savedUser = userService.saveUser(userRequest);
        return ResponseEntity.status(201).body(ApiResponse.success(savedUser, "User created successfully", 201));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(user -> ResponseEntity.ok(ApiResponse.success(user, "User retrieved successfully", 200)))
                .orElseGet(() -> ResponseEntity.status(404).body(ApiResponse.failure("User not found", 404)));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRequest userDetails) {
        try {
            UserResponse updatedUser = userService.updateUser(userId, userDetails);
            return ResponseEntity.ok(ApiResponse.success(updatedUser, "User updated successfully", 200));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponse.failure(e.getMessage(), 404));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(ApiResponse.success(null, "User deleted successfully", 200));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(ApiResponse.failure("User not found", 404));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users, "Users retrieved successfully", 200));
    }
}