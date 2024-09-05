package com.aman.chat_application.ServiceImpl;

import com.aman.chat_application.Config.Jwt.JwtService;
import com.aman.chat_application.Dto.LoginRequestDto;
import com.aman.chat_application.Dto.PasswordResetRequestDto;
import com.aman.chat_application.Dto.UserDto.ChangePasswordRequestDto;
import com.aman.chat_application.Dto.UserDto.UserCreateDto;
import com.aman.chat_application.Dto.UserDto.UserDto;
import com.aman.chat_application.Enumerator.AppRole;
import com.aman.chat_application.Exception.EmailAlreadyExistsException;
import com.aman.chat_application.Exception.RoleNotFoundException;
import com.aman.chat_application.Exception.UsernameAlreadyExistsException;
import com.aman.chat_application.Mapper.UserMapper;
import com.aman.chat_application.Model.Role;
import com.aman.chat_application.Model.User;
import com.aman.chat_application.Repository.RoleRepository;
import com.aman.chat_application.Repository.UserRepository;
import com.aman.chat_application.Service.AuthService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.commons.codec.binary.Base32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender, RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public UserDto registerUser(@Valid UserCreateDto userCreateDto) {
        logger.debug("Creating user with username: {}", userCreateDto.getUserName());

        // Check if the username is blank
        if (userCreateDto.getUserName() == null || userCreateDto.getUserName().isBlank()) {
            logger.error("Username cannot be blank.");
            throw new IllegalArgumentException("Username cannot be blank.");
        }

        // Check if the username already exists
        if (userRepository.existsByUserName(userCreateDto.getUserName())) {
            logger.error("Username already exists: {}", userCreateDto.getUserName());
            throw new UsernameAlreadyExistsException("Username already exists.");
        }

        // Check if the email already exists
        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            logger.error("Email already exists: {}", userCreateDto.getEmail());
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        // Validate password
        if (userCreateDto.getPassword() == null || userCreateDto.getPassword().isEmpty()) {
            logger.error("Password cannot be null or empty.");
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }

        // Map the user creation DTO to the User entity
        User user = UserMapper.INSTANCE.mapperUserFromCreate(userCreateDto);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        // Set default role if none provided or if the provided role does not exist
        AppRole appRole;
        try {
            appRole = userCreateDto.getRoleName() != null ? AppRole.valueOf(userCreateDto.getRoleName()) : AppRole.ROLE_USER;
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid role name provided: {}. Defaulting to ROLE_USER.", userCreateDto.getRoleName());
            appRole = AppRole.ROLE_USER; // Default role if invalid
        }

        Role role = roleRepository.findByRoleName(appRole)
                .orElseGet(() -> roleRepository.findByRoleName(AppRole.ROLE_USER)
                        .orElseGet(() -> {
                            logger.info("Default role ROLE_USER not found in the database. Creating new default role.");
                            return roleRepository.save(new Role(AppRole.ROLE_USER));
                        }));

        user.setRole(role);

        // Save the user to the database
        user = userRepository.save(user);

        logger.info("User created successfully with ID: {}", user.getUserId());

        // Generate a token after successful registration
        String token = jwtService.generateTokenUser(user);

        // Return the User DTO
        return UserMapper.INSTANCE.mapperUserDto(user);
    }

    @Override
    @Transactional
    public void changePassword(@Valid ChangePasswordRequestDto changePasswordRequestDto) {
        User user = userRepository.findByEmail(changePasswordRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(changePasswordRequestDto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void enableTwoFactorAuthentication(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setTwoFactorySecret(generateTwoFactorSecret());
        user.setTwoFactorEnabled(true);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void disableTwoFactorAuthentication(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setTwoFactorySecret(null);
        user.setTwoFactorEnabled(false);
        userRepository.save(user);
    }

    @Override
    public String loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUserName(loginRequestDto.getUsernameOrEmail())
                .orElseGet(() -> userRepository.findByEmail(loginRequestDto.getUsernameOrEmail())
                        .orElseThrow(() -> new RuntimeException("User not found")));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateTokenUser(user);
    }

    @Override
    @Transactional
    public void resetPassword(PasswordResetRequestDto passwordResetRequestDto) {
        User user = userRepository.findByEmail(passwordResetRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String resetToken = generateResetToken();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

        user.setResetToken(resetToken);
        user.setTokenExpiryDate(expiryDate);
        userRepository.save(user);

        sendResetPasswordEmail(user.getEmail(), resetToken);
    }


    private String generateTwoFactorSecret() {
        SecureRandom random = new SecureRandom();
        byte[] buffer = new byte[10];
        random.nextBytes(buffer);

        Base32 base32 = new Base32();
        return base32.encodeToString(buffer).replace("=", "");
    }

    private String generateResetToken() {
        return UUID.randomUUID().toString();
    }

    private void sendResetPasswordEmail(String email, String resetToken) {
        String resetLink = "https://your-app-url.com/reset-password?token=" + resetToken;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Click the link below to reset your password:\n" + resetLink);

        mailSender.send(message);
    }
}
