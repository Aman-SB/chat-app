package com.aman.chat_application.Dto.UserDto;

import com.aman.chat_application.Dto.Chat.ChatDTO;
import com.aman.chat_application.Dto.Role.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
public class UserDto {
    private Integer userId;
    private String userName;
    private String fullName;
    private String email;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String signUpMethod;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private RoleDTO role;
    private Set<ChatDTO> chats;

    public UserDto(Integer userId, String userName, String fullName, String email, boolean accountNonLocked, boolean accountNonExpired, boolean credentialsNonExpired, boolean enabled, String signUpMethod, LocalDateTime createdDate, LocalDateTime updateDate, RoleDTO role, Set<ChatDTO> chats) {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.signUpMethod = signUpMethod;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.role = role;
        this.chats = chats;
    }

    public UserDto(UserDto userDto) {
        this.userId = userDto.userId;
        this.userName = userDto.userName;
        this.fullName = userDto.fullName;
        this.email = userDto.email;
        this.accountNonLocked = userDto.accountNonLocked;
        this.accountNonExpired = userDto.accountNonExpired;
        this.credentialsNonExpired = userDto.credentialsNonExpired;
        this.enabled = userDto.enabled;
        this.signUpMethod = userDto.signUpMethod;
        this.createdDate = userDto.createdDate;
        this.updateDate = userDto.updateDate;
        this.role = userDto.role;
        this.chats = userDto.chats;
    }
}
