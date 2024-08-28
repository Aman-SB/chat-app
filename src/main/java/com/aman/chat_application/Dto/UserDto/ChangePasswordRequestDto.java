package com.aman.chat_application.Dto.UserDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordRequestDto {

    private String oldPassword;
    private String newPassword;

    // Constructor with fields if needed
    public ChangePasswordRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
