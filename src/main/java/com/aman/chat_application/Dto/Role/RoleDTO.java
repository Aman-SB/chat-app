package com.aman.chat_application.Dto.Role;

import com.aman.chat_application.Enumerator.AppRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Integer roleId;
    private AppRole roleName;
}
