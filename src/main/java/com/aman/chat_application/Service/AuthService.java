package com.aman.chat_application.Service;

import com.aman.chat_application.Dto.LoginRequestDto;
import com.aman.chat_application.Dto.PasswordResetRequestDto;
import com.aman.chat_application.Dto.UserDto.ChangePasswordRequestDto;
import com.aman.chat_application.Dto.UserDto.TokenUserDto;
import com.aman.chat_application.Dto.UserDto.UserCreateDto;
import com.aman.chat_application.Dto.UserDto.UserDto;
import org.springframework.stereotype.Service;

public interface AuthService {

    TokenUserDto registerUser(UserCreateDto userCreateDto);

    void changePassword(ChangePasswordRequestDto changePasswordRequestDto);

    void enableTwoFactorAuthentication(String email);

    void disableTwoFactorAuthentication(String email);

    String loginUser(LoginRequestDto loginRequestDto);

    void resetPassword(PasswordResetRequestDto passwordResetRequestDto);
}
