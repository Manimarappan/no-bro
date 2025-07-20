package com.property.no_bro.service;

import com.property.no_bro.dto.request.UserRequest;
import com.property.no_bro.dto.request.UserUpdateRequest;
import com.property.no_bro.dto.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse saveUser(UserRequest userRequest);
    Optional<UserResponse> getUserById(Long userId);
    UserResponse updateUser(Long userId, UserUpdateRequest userDetails);
    void deleteUser(Long userId);
    List<UserResponse> getAllUsers();
    Optional<UserResponse> findByEmail(String email);

    UserResponse login(String email, String password);

    UserResponse updateProfilePic(Long userId, MultipartFile profilePic);
}