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
@AllArgsConstructor
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
}
