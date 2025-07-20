package com.property.no_bro.service.impl;

import com.property.no_bro.dto.request.UserRequest;
import com.property.no_bro.dto.request.UserUpdateRequest;
import com.property.no_bro.dto.response.UserResponse;
import com.property.no_bro.model.User;
import com.property.no_bro.repository.UserRepository;
import com.property.no_bro.service.UserService;
import com.property.no_bro.exception.InvalidInputException;
import com.property.no_bro.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));// Hash this in a real app
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setUserType(userRequest.getUserType());
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }




    @Override
    public Optional<UserResponse> getUserById(Long userId) {
        return userRepository.findById(userId).map(UserResponse::new);
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setUserType(userRequest.getUserType());

        user = userRepository.save(user);
        return new UserResponse(user);
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

    @Override
    public UserResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidInputException("Invalid email or password"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidInputException("Invalid email or password");
        }
        return new UserResponse(user);
    }

    @Override
    public UserResponse updateProfilePic(Long userId, MultipartFile profilePic) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (profilePic == null || profilePic.isEmpty()) {
            throw new RuntimeException("No profile picture provided");
        }

        try {
            if (!"image/jpeg".equals(profilePic.getContentType())) {
                throw new IllegalArgumentException("Only JPEG images are allowed");
            }
            byte[] fileBytes = profilePic.getBytes();
            String base64Image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(fileBytes);
            user.setProfilePic(base64Image);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process image: " + e.getMessage());
        }

        user = userRepository.save(user);
        return new UserResponse(user);
    }
}