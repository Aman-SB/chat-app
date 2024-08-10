package com.aman.chat_application.ServiceImpl;

import com.aman.chat_application.Dto.UpdateRoleRequestDto;
import com.aman.chat_application.Dto.UserDto.UserDto;
import com.aman.chat_application.Mapper.UserMapper;
import com.aman.chat_application.Model.User;
import com.aman.chat_application.Repository.RoleRepository;
import com.aman.chat_application.Repository.UserRepository;
import com.aman.chat_application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper.INSTANCE::mapperUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public String updateUserRole(UpdateRoleRequestDto updateRoleRequestDto) {

        return null;
    }
}
