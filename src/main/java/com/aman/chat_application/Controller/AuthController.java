package com.aman.chat_application.Controller;

import com.aman.chat_application.Dto.LoginRequestDto;
import com.aman.chat_application.Dto.PasswordResetRequestDto;
import com.aman.chat_application.Dto.UserDto.ChangePasswordRequestDto;
import com.aman.chat_application.Dto.UserDto.UserCreateDto;
import com.aman.chat_application.Dto.UserDto.UserDto;
import com.aman.chat_application.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register a new user (Sign Up)
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserCreateDto userCreateDto) {
        return new ResponseEntity<>(authService.registerUser(userCreateDto), HttpStatus.CREATED);
    }

    // User Login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        String token = authService.loginUser(loginRequestDto);
        return ResponseEntity.ok(token);
    }

    // Reset Password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequestDto passwordResetRequestDto) {
        authService.resetPassword(passwordResetRequestDto);
        return ResponseEntity.ok("Password reset link has been sent to the email.");
    }

    // Change Password
    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        authService.changePassword(changePasswordRequestDto);
        return ResponseEntity.ok("Password changed successfully.");
    }

    // Enable Two-Factor Authentication
    @PatchMapping("/two-factor/enable")
    public ResponseEntity<String> enableTwoFactorAuthentication(@RequestBody String email) {
        authService.enableTwoFactorAuthentication(email);
        return ResponseEntity.ok("Two-factor authentication has been enabled.");
    }

    // Disable Two-Factor Authentication
    @PatchMapping("/two-factor/disable")
    public ResponseEntity<String> disableTwoFactorAuthentication(@RequestBody String email) {
        authService.disableTwoFactorAuthentication(email);
        return ResponseEntity.ok("Two-factor authentication has been disabled.");
    }
}
