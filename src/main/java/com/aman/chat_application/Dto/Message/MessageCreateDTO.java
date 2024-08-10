package com.aman.chat_application.Dto.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreateDTO {

    @NotNull
    private Integer chatId;

    @NotNull
    private Integer userId;

    @NotBlank
    private String content;

    @NotNull
    private LocalDateTime timestamp;
}
