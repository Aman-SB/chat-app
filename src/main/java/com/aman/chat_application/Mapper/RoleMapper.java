package com.aman.chat_application.Mapper;

import com.aman.chat_application.Dto.Role.RoleDTO;
import com.aman.chat_application.Enumerator.AppRole;
import com.aman.chat_application.Model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target ="roleName" , source ="roleName" )
    Role mapperRole(RoleDTO roleDTO);

    RoleDTO mapperRoleDto(Role role);

}
