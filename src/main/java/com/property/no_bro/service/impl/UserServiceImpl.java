package com.property.no_bro.service.impl;

import com.property.no_bro.dto.request.UserRequest;
import com.property.no_bro.dto.response.UserResponse;
import com.property.no_bro.model.User;
import com.property.no_bro.repository.UserRepository;
import com.property.no_bro.service.UserService;
import com.property.no_bro.exception.InvalidInputException;
import com.property.no_bro.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        if (userRequest.getEmail() == null || userRequest.getEmail().trim().isEmpty()) {
            throw new InvalidInputException("Email is required");
        }
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new InvalidInputException("Email already exists");
        }

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword()); // Hash this in a real app
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setUserType(userRequest.getUserType());
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        user.setStatus("ACTIVE");

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    @Override
    public Optional<UserResponse> getUserById(Long userId) {
        return userRepository.findById(userId).map(UserResponse::new);
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequest userDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        if (userDetails.getFirstName() != null) user.setFirstName(userDetails.getFirstName());
        if (userDetails.getLastName() != null) user.setLastName(userDetails.getLastName());
        if (userDetails.getPhoneNumber() != null) user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        return new UserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponse> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserResponse::new);
    }
}