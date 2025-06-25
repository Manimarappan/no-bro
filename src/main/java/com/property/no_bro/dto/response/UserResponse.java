package com.property.no_bro.dto.response;

import com.property.no_bro.enums.UserType;
import com.property.no_bro.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserType userType;
    private String profilePic;
    private boolean isVerified;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;

    public UserResponse(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.userType = user.getUserType();
        this.profilePic = user.getProfilePic();
        this.isVerified = user.isVerified();
        this.lastLogin = user.getLastLogin();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.status = user.getStatus();
    }
}