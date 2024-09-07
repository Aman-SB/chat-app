package com.aman.chat_application.Controller;

import com.aman.chat_application.Dto.Chat.ChatDTO;
import com.aman.chat_application.Dto.UpdateRoleRequestDto;
import com.aman.chat_application.Dto.UserDto.*;
import com.aman.chat_application.Model.User;
import com.aman.chat_application.Service.FriendService;
import com.aman.chat_application.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    private final FriendService friendService;

    @Autowired
    public UserController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

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

    //update user role
    @PostMapping("/{userId}/role")
    public ResponseEntity<String> updateUserRole(@RequestBody UpdateRoleRequestDto updateRoleRequestDto){
        return ResponseEntity.ok(userService.updateUserRole(updateRoleRequestDto));
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
        List<User> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/friends/request")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequestDto requestDto) {
        friendService.sendFriendRequest(requestDto);
        return ResponseEntity.ok("Friend request sent.");
    }

    @PostMapping("/friends/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam Integer requestId) {
        friendService.acceptFriendRequest(requestId);
        return ResponseEntity.ok("Friend request accepted.");
    }

    @PostMapping("/friends/decline")
    public ResponseEntity<String> declineFriendRequest(@RequestParam Integer requestId) {
        friendService.declineFriendRequest(requestId);
        return ResponseEntity.ok("Friend request declined.");
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<Set<UserDto>> getAllFriend(@PathVariable(value = "userId") Integer userId){
        return ResponseEntity.ok(userService.getAllFriends(userId));
    }
}
