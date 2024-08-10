package com.aman.chat_application.Dto.Message;

import com.aman.chat_application.Dto.UserDto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MessageResponseDTO {

    private Integer messageId;
    private String content;
    private LocalDateTime timestamp;
    private UserDto user;
}
