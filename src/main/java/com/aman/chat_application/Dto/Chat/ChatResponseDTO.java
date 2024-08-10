package com.aman.chat_application.Dto.Chat;

import com.aman.chat_application.Dto.Message.MessageDTO;
import com.aman.chat_application.Dto.UserDto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class ChatResponseDTO {

    private Integer chatId;
    private String chatName;
    private LocalDateTime createdAt;
    private Set<UserDto> users;
    private Set<MessageDTO> messages;
}
