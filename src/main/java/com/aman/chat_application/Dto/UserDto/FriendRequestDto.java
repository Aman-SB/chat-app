package com.aman.chat_application.Dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDto {

    // The ID of the user sending the friend request
    @NotNull
    private Integer senderId;

    // The ID of the user receiving the friend request
    @NotNull
    private Integer receiverId;

    // Optional message or note with the friend request (if applicable)
    private String message;
}
