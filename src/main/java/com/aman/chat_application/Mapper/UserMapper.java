package com.aman.chat_application.Mapper;

import com.aman.chat_application.Dto.UserDto.UserCreateDto;
import com.aman.chat_application.Dto.UserDto.UserDto;
import com.aman.chat_application.Dto.UserDto.UserUpdateDto;
import com.aman.chat_application.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

//    User mapperUser(UserDto userDto);

    UserDto mapperUserDto(User user);

//    UserCreateDto mapperUserCreateDto(User user);

//    UserUpdateDto mapperUserUpdateDto(User user);

}
