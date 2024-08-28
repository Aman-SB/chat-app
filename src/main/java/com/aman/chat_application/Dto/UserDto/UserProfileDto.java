package com.aman.chat_application.Dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {
    private Integer userId;
    private String userName;
    private String fullName;
    private String bio; // Add a bio if needed for profile
    private String profilePictureUrl; // Add if you want to include a profile picture
    private LocalDateTime createdDate;
}