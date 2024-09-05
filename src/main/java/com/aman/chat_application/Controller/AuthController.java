package com.aman.chat_application.Controller;

import com.aman.chat_application.Dto.LoginRequestDto;
import com.aman.chat_application.Dto.PasswordResetRequestDto;
import com.aman.chat_application.Dto.UserDto.ChangePasswordRequestDto;
import com.aman.chat_application.Dto.UserDto.UserCreateDto;
import com.aman.chat_application.Dto.UserDto.UserDto;
import com.aman.chat_application.Service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register a new user (Sign Up)
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        logger.debug("Incoming UserCreateDto: {}", userCreateDto);

        // Log individual fields if needed
        logger.debug("Username: {}, Email: {}, Password: {}", userCreateDto.getUserName(), userCreateDto.getEmail(), userCreateDto.getPassword());
        return new ResponseEntity<>(authService.registerUser(userCreateDto), HttpStatus.CREATED);
    }

    // User Login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        String token = authService.loginUser(loginRequestDto);
        return ResponseEntity.ok(token);
    }

    // Logout endpoint
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        logger.debug("Logging out user with token: {}", token);

        // Optionally, you might want to perform some actions here
        // authService.invalidateToken(token); // If you had a mechanism to handle this

        return ResponseEntity.ok("User logged out successfully.");
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
