package com.aman.chat_application.Dto.UserDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordRequestDto {

    @NotNull
    private String email;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
