package com.aman.chat_application.Service;

import com.aman.chat_application.Dto.UserDto.UserDto;
import com.aman.chat_application.Model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<UserDto> getAllUsers();
}
