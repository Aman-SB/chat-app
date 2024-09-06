package com.aman.chat_application.Mapper;

import com.aman.chat_application.Dto.UserDto.*;
import com.aman.chat_application.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface
UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //coming request
    User mapperUser(UserDto userDto);

    UserDto mapperUserDto(User user);

    User mapperUserFromCreate(UserCreateDto userCreateDto);

    UserUpdateDto mapperUserUpdateDto(User user);

    UserProfileDto mapperUserProfileDto(User user);

//    @Mapping(target = "token", source = "token") // Map the token separately
//    TokenUserDto mapUserDtoToTokenUserDto(UserDto userDto, String token);

    // Custom method to handle the token
    default TokenUserDto mapUserDtoToTokenUserDto(UserDto userDto, String token) {
        if (userDto == null) {
            return null;
        }

        TokenUserDto tokenUserDto = new TokenUserDto();
        tokenUserDto.setUserId(userDto.getUserId());
        tokenUserDto.setUserName(userDto.getUserName());
        tokenUserDto.setFullName(userDto.getFullName());
        tokenUserDto.setEmail(userDto.getEmail());
        tokenUserDto.setAccountNonLocked(userDto.isAccountNonLocked());
        tokenUserDto.setAccountNonExpired(userDto.isAccountNonExpired());
        tokenUserDto.setCredentialsNonExpired(userDto.isCredentialsNonExpired());
        tokenUserDto.setEnabled(userDto.isEnabled());
        tokenUserDto.setSignUpMethod(userDto.getSignUpMethod());
        tokenUserDto.setCreatedDate(userDto.getCreatedDate());
        tokenUserDto.setUpdateDate(userDto.getUpdateDate());
        tokenUserDto.setRole(userDto.getRole());
        tokenUserDto.setChats(userDto.getChats()); // Handle Set<ChatDTO> if needed
        tokenUserDto.setToken(token); // Set the token

        return tokenUserDto;
    }
}
