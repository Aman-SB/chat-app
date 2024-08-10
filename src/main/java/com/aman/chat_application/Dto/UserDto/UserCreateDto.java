package com.aman.chat_application.Dto.UserDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateDto {

    @NotBlank
    @Size(max = 20)
    private String userName;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    private String signUpMethod;
}
