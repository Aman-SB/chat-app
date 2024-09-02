package com.aman.chat_application.Controller;

import com.aman.chat_application.Dto.Chat.ChatDTO;
import com.aman.chat_application.Dto.UpdateRoleRequestDto;
import com.aman.chat_application.Dto.UserDto.ChangePasswordRequestDto;
import com.aman.chat_application.Dto.UserDto.UserCreateDto;
import com.aman.chat_application.Dto.UserDto.UserDto;
import com.aman.chat_application.Dto.UserDto.UserProfileDto;
import com.aman.chat_application.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Create User (Sign Up)
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userService.createUser(userCreateDto),HttpStatus.CREATED);
    }

    // Get User by ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.getUserProfile(userId),HttpStatus.FOUND);
    }

    // Get All Users
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }


    // Update User
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer userId, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userId, userDto));
    }

    // Delete User
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // Update User's Profile Picture
    @PatchMapping("/{userId}/profile-picture")
    public ResponseEntity<UserDto> updateUserProfilePicture(@PathVariable Integer userId, @RequestBody String profilePictureUrl) {
        return ResponseEntity.ok(userService.updateUserProfilePicture(userId, profilePictureUrl));
    }

    // Enable/Disable Two-Factor Authentication
    @PatchMapping("/{userId}/two-factor")
    public ResponseEntity<UserDto> toggleTwoFactorAuthentication(@PathVariable Integer userId, @RequestBody boolean enable) {
        // Example: userService.toggleTwoFactorAuthentication(userId, enable);
        return ResponseEntity.ok(userService.toggleTwoFactorAuthentication(userId, enable));
    }

    // Assign Role to User
    @PatchMapping("/{userId}/role")
    public ResponseEntity<String> assignRoleToUser(@PathVariable Integer userId, @RequestBody UpdateRoleRequestDto updateRoleRequestDto) {
        updateRoleRequestDto.setUserId(userId);
        return ResponseEntity.ok(userService.updateUserRole(updateRoleRequestDto));
    }

    // Get User Role
    @GetMapping("/{userId}/get/role")
    public ResponseEntity<String> getUserRole(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUserRole(userId));
    }

    // Lock/Unlock User Account
    @PatchMapping("/{userId}/lock")
    public ResponseEntity<UserDto> lockUnlockUserAccount(@PathVariable Integer userId, @RequestBody boolean lock) {
        // Example: userService.lockUnlockUserAccount(userId, lock);
        return ResponseEntity.ok(userService.lockUnlockUserAccount(userId, lock));
    }

    // Enable/Disable User Account
    @PatchMapping("/{userId}/enable")
    public ResponseEntity<UserDto> enableDisableUserAccount(@PathVariable Integer userId, @RequestBody boolean enable) {
        return ResponseEntity.ok(userService.enableDisableUserAccount(userId, enable));
    }

    // Get User's Chats
    @GetMapping("/{userId}/chats")
    public ResponseEntity<List<ChatDTO>> getUserChats(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUserChats(userId));
    }

    // Add User to Chat
    @PostMapping("/{userId}/chats/{chatId}")
    public ResponseEntity<String> addUserToChat(@PathVariable Integer userId, @PathVariable Integer chatId) {
        return ResponseEntity.ok(userService.addUserToChat(userId, chatId));
    }

    // Remove User from Chat
    @DeleteMapping("/{userId}/chats/delete/{chatId}")
    public ResponseEntity<String> removeUserFromChat(@PathVariable Integer userId, @PathVariable Integer chatId) {
        return ResponseEntity.ok(userService.removeUserFromChat(userId, chatId));
    }

    // Change Password
    @PatchMapping("/{userId}/password")
    public ResponseEntity<String> changePassword(@PathVariable Integer userId, @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        return ResponseEntity.ok(userService.changePassword(userId, changePasswordRequestDto));
    }

    // Reset Password
    @PostMapping("/auth/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody String email) {
        return ResponseEntity.ok(userService.resetPassword(email));
    }

    // Extend Account Expiry
    @PatchMapping("/{userId}/extend-expiry")
    public ResponseEntity<UserDto> extendAccountExpiry(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.extendAccountExpiry(userId));
    }

    // Update User Bio
    @PatchMapping("/{userId}/bio")
    public ResponseEntity<UserDto> updateUserBio(@PathVariable Integer userId, @RequestBody String bio) {
        return ResponseEntity.ok(userService.updateUserBio(userId, bio));
    }


}
