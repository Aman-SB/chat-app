package com.aman.chat_application.ServiceImpl;

import com.aman.chat_application.Dto.UpdateRoleRequestDto;
import com.aman.chat_application.Dto.UserDto.UserDto;
import com.aman.chat_application.Enumerator.AppRole;
import com.aman.chat_application.Exception.RoleNotFoundException;
import com.aman.chat_application.Exception.UserNotFoundException;
import com.aman.chat_application.Mapper.UserMapper;
import com.aman.chat_application.Model.Role;
import com.aman.chat_application.Model.User;
import com.aman.chat_application.Repository.RoleRepository;
import com.aman.chat_application.Repository.UserRepository;
import com.aman.chat_application.Service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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
        User user = userRepository.findById(updateRoleRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found by ID"));

        AppRole appRole = AppRole.valueOf(updateRoleRequestDto.getRole());
        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RoleNotFoundException("Role not found by this RoleName"));
        user.setRole(role);
        userRepository.save(user);
        return "Role have been updated";
    }

    @Override
    public UserDto getUser(Integer userId) {
        return userRepository.findById(userId)
                .map(UserMapper.INSTANCE::mapperUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with this userId : " + userId));
    }


}
