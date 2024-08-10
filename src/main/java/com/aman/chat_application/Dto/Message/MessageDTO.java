package com.aman.chat_application.Dto.Message;

import com.aman.chat_application.Dto.Chat.ChatDTO;
import com.aman.chat_application.Dto.UserDto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {

    private Integer messageId;
    private String content;
    private LocalDateTime timestamp;
    private UserDto user;
    private ChatDTO chat;
}
