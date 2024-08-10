package com.aman.chat_application.Dto.Chat;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatCreateDTO {

    private String chatName;

    @NotNull
    private LocalDateTime createdAt;
}
