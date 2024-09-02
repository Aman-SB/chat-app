package com.aman.chat_application.Service;

import com.aman.chat_application.Dto.LoginRequestDto;
import com.aman.chat_application.Dto.PasswordResetRequestDto;
import com.aman.chat_application.Dto.UserDto.ChangePasswordRequestDto;
import com.aman.chat_application.Dto.UserDto.UserCreateDto;
import com.aman.chat_application.Dto.UserDto.UserDto;
import org.springframework.stereotype.Service;

public interface AuthService {
    
    public UserDto registerUser(UserCreateDto userCreateDto);

    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto);

    public void enableTwoFactorAuthentication(String email);

    public void disableTwoFactorAuthentication(String email);

    public String loginUser(LoginRequestDto loginRequestDto);

    public void resetPassword(PasswordResetRequestDto passwordResetRequestDto);
}
