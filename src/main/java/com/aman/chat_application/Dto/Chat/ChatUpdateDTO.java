package com.aman.chat_application.Dto.Chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatUpdateDTO {

    private String chatName;

    @NotNull
    private LocalDateTime createdAt;
}
