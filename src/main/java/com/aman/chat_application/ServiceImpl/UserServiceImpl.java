package com.aman.chat_application.ServiceImpl;

import com.aman.chat_application.Dto.UserDto.UserDto;
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
        return Optional.of(userRepository.findAll())
                .orElse(Collections.emptyList())
                .stream()
                .map(user -> )
                .collect(Collectors.toList());
    }
}
