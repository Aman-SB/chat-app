package com.aman.chat_application.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    
    private String usernameOrEmail; // Could be username or email depending on your login strategy
    private String password;
}
