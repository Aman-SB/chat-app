package com.aman.chat_application.ServiceImpl;

import com.aman.chat_application.Dto.Chat.ChatDTO;
import com.aman.chat_application.Dto.UpdateRoleRequestDto;
import com.aman.chat_application.Dto.UserDto.ChangePasswordRequestDto;
import com.aman.chat_application.Dto.UserDto.UserCreateDto;
import com.aman.chat_application.Dto.UserDto.UserDto;
import com.aman.chat_application.Dto.UserDto.UserProfileDto;
import com.aman.chat_application.Enumerator.AppRole;
import com.aman.chat_application.Exception.*;
import com.aman.chat_application.Mapper.ChatMapper;
import com.aman.chat_application.Mapper.UserMapper;
import com.aman.chat_application.Model.Chat;
import com.aman.chat_application.Model.Role;
import com.aman.chat_application.Model.User;
import com.aman.chat_application.Repository.ChatRepository;
import com.aman.chat_application.Repository.RoleRepository;
import com.aman.chat_application.Repository.UserRepository;
import com.aman.chat_application.Service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ChatRepository chatRepository;

    private final JavaMailSender javaMailSender;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ChatRepository chatRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.chatRepository = chatRepository;
        this.javaMailSender=javaMailSender;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper.INSTANCE::mapperUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public String updateUserRole(UpdateRoleRequestDto updateRoleRequestDto) {
        User user = userRepository.findById(updateRoleRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found by ID"));

        AppRole appRole = AppRole.valueOf(updateRoleRequestDto.getRole());
        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RoleNotFoundException("Role not found by this RoleName"));
        user.setRole(role);
        userRepository.save(user);
        return "Role have been updated";
    }

    @Override
    public UserDto getUser(Integer userId) {
        return userRepository.findById(userId)
                .map(UserMapper.INSTANCE::mapperUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));
    }

    @Override
    public UserProfileDto getUserProfile(Integer userId) {
        return userRepository.findById(userId)
                .map(UserMapper.INSTANCE::mapperUserProfileDto)
                .orElseThrow(() -> new UserNotFoundException("User not Found : " + userId));
    }

    @Override
    public UserDto createUser(UserCreateDto userCreateDto) {
        logger.debug("Creating user with username: {}", userCreateDto.getUserName());

        if (userRepository.existsByUserName(userCreateDto.getUserName())) {
            logger.error("Username already exists: {}", userCreateDto.getUserName());
            throw new UsernameAlreadyExistsException("Username already exists.");
        }

        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            logger.error("Email already exists: {}", userCreateDto.getEmail());
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        if (userCreateDto.getPassword() == null || userCreateDto.getPassword().isEmpty()) {
            logger.error("Password cannot be null or empty.");
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        User user = UserMapper.INSTANCE.mapperUserfromCreate(userCreateDto);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user = userRepository.save(user);

        logger.info("User created successfully with ID: {}", user.getUserId());
        return UserMapper.INSTANCE.mapperUserDto(user);
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));

        User updatedUser = UserMapper.INSTANCE.mapperUser(userDto);
        updatedUser.setUserId(existingUser.getUserId());
        updatedUser.setCreatedDate(existingUser.getCreatedDate());
        updatedUser.setPassword(existingUser.getPassword()); // Preserve existing password
        userRepository.save(updatedUser);
        return UserMapper.INSTANCE.mapperUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));
        userRepository.delete(user);
    }

    @Override
    public UserDto updateUserProfilePicture(Integer userId, String profilePictureUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));

        user.setProfilePictureUrl(profilePictureUrl);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.mapperUserDto(user);
    }

    @Override
    public UserDto toggleTwoFactorAuthentication(Integer userId, boolean enable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));

        user.setTwoFactorEnabled(enable);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.mapperUserDto(user);
    }

    @Override
    public String getUserRole(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));

        return user.getRole().getRoleName().toString();
    }

    @Override
    public UserDto lockUnlockUserAccount(Integer userId, boolean lock) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));

        user.setAccountNonLocked(!lock);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.mapperUserDto(user);
    }

    @Override
    public UserDto enableDisableUserAccount(Integer userId, boolean enable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));

        user.setEnabled(enable);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.mapperUserDto(user);
    }

    @Override
    public List<ChatDTO> getUserChats(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));

        return user.getChats()
                .stream()
                .map(ChatMapper.INSTANCE::mapperChatDto)
                .collect(Collectors.toList());
    }

    @Override
    public String addUserToChat(Integer userId, Integer chatId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));

         Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException("Chat not found with this chatId : " + chatId));
         user.getChats().add(chat);

        userRepository.save(user);
        return "User added to chat";
    }

    @Override
    public String removeUserFromChat(Integer userId, Integer chatId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));

         Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException("Chat not found with this chatId : " + chatId));
         user.getChats().remove(chat);

        userRepository.save(user);
        return "User removed from chat";
    }

    @Override
    public String changePassword(Integer userId, ChangePasswordRequestDto changePasswordRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));

        if (!passwordEncoder.matches(changePasswordRequestDto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect.");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        userRepository.save(user);
        return "Password changed successfully";
    }

    @Override
    public String resetPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with this email: " + email));

        // Generate a unique password reset token
        String resetToken = UUID.randomUUID().toString();

        // Set the reset token and its expiry time on the user
        user.setResetToken(resetToken);
        user.setTokenExpiryDate(LocalDateTime.now().plusHours(1)); // Token expires in 1 hour
        userRepository.save(user);

        // Construct the password reset link
        String resetLink = "http://yourdomain.com/reset-password?token=" + resetToken;

        // Send the reset link via email
        sendResetPasswordEmail(user.getEmail(), resetLink);

        return "Password reset link has been sent to the email.";
    }

    @Override
    public UserDto extendAccountExpiry(Integer userId) {
        // Fetch the user by ID, throw exception if not found2
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId: " + userId));

        // Extend the account expiry date (e.g., by 30 days from now or from the current expiry date)
        LocalDate currentExpiryDate = user.getAccountExpiryDate() != null ? user.getAccountExpiryDate() : LocalDate.now();
        LocalDate newExpiryDate = currentExpiryDate.plusDays(30); // Extend by 30 days
        user.setAccountExpiryDate(newExpiryDate);

        // Optionally update the last update date
        user.setUpdateDate(LocalDateTime.now());

        // Save the updated user entity to the database
        userRepository.save(user);

        // Map the user entity to UserDto and return the updated information
        return UserMapper.INSTANCE.mapperUserDto(user);
    }

    // Helper method to send the password reset email
    private void sendResetPasswordEmail(String email, String resetLink) {
        // Email sending logic here, could use JavaMailSender or any other email service
        String subject = "Password Reset Request";
        String body = "Click the following link to reset your password: " + resetLink;

        // Example using JavaMailSender (if configured in your application)
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        // Assuming JavaMailSender is configured in your project
        javaMailSender.send(message); // javaMailSender is a bean you need to configure
    }

    @Override
    public UserDto updateUserBio(Integer userId, String bio) {
        // Find the user by ID; throw an exception if not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId: " + userId));
        user.setBio(bio);

        user = userRepository.save(user);

        return UserMapper.INSTANCE.mapperUserDto(user);
    }


}
