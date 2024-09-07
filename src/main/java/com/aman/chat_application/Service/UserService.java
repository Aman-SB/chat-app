package com.aman.chat_application.Service;

import com.aman.chat_application.Dto.Chat.ChatDTO;
import com.aman.chat_application.Dto.UpdateRoleRequestDto;
import com.aman.chat_application.Dto.UserDto.ChangePasswordRequestDto;
import com.aman.chat_application.Dto.UserDto.UserCreateDto;
import com.aman.chat_application.Dto.UserDto.UserDto;
import com.aman.chat_application.Dto.UserDto.UserProfileDto;
import com.aman.chat_application.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface UserService {

    public List<UserDto> getAllUsers();

    public String updateUserRole(UpdateRoleRequestDto updateRoleRequestDto);

    public UserDto getUser(Integer userId);

    UserProfileDto getUserProfile(Integer userId);

    UserDto createUser(UserCreateDto userCreateDto);

    UserDto updateUser(Integer userId, UserDto userDto);

    void deleteUser(Integer userId);

    UserDto updateUserProfilePicture(Integer userId, String profilePictureUrl);

    UserDto toggleTwoFactorAuthentication(Integer userId, boolean enable);

    String getUserRole(Integer userId);

    UserDto lockUnlockUserAccount(Integer userId, boolean lock);

    UserDto enableDisableUserAccount(Integer userId, boolean enable);

    List<ChatDTO> getUserChats(Integer userId);

    String addUserToChat(Integer userId, Integer chatId);

    String removeUserFromChat(Integer userId, Integer chatId);

    String changePassword(Integer userId, ChangePasswordRequestDto changePasswordRequestDto);

    String resetPassword(String email);

    UserDto extendAccountExpiry(Integer userId);

    UserDto updateUserBio(Integer userId, String bio);

    List<User> searchUsers(String query);

    Set<UserDto> getAllFriends(Integer userId);
}
