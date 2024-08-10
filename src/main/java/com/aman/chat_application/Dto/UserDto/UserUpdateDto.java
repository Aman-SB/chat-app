package com.aman.chat_application.Dto.UserDto;

import com.aman.chat_application.Dto.Role.RoleDTO;
import jakarta.validation.constraints.Size;

public class UserUpdateDto {

    @Size(max = 20)
    private String userName;

    private String fullName;

    @Size(max = 50)
    private String email;

    @Size(max = 120)
    private String password;

    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String signUpMethod;
    private RoleDTO role;
}
