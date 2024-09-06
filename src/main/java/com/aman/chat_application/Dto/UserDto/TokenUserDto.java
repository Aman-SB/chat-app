package com.aman.chat_application.Dto.UserDto;

import com.aman.chat_application.Dto.Chat.ChatDTO;
import com.aman.chat_application.Dto.Role.RoleDTO;
import com.aman.chat_application.Mapper.UserMapper;
import com.aman.chat_application.Model.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TokenUserDto extends UserDto{

    private String token;

    public TokenUserDto(String token, User user) {
        super(UserMapper.INSTANCE.mapperUserDto(user));
        this.token = token;
    }

}
