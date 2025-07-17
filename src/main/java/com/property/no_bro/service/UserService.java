package com.property.no_bro.service;

import com.property.no_bro.dto.request.UserRequest;
import com.property.no_bro.dto.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse saveUser(UserRequest userRequest);
    Optional<UserResponse> getUserById(Long userId);
    UserResponse updateUser(Long userId, UserRequest userDetails);
    void deleteUser(Long userId);
    List<UserResponse> getAllUsers();
    Optional<UserResponse> findByEmail(String email);

    UserResponse login(String email, String password);
}