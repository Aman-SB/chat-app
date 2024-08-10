package com.aman.chat_application.Controller;

import com.aman.chat_application.Dto.UpdateRoleRequestDto;
import com.aman.chat_application.Dto.UserDto.UserDto;
import com.aman.chat_application.Enumerator.AppRole;
import com.aman.chat_application.Model.Role;
import com.aman.chat_application.Model.User;
import com.aman.chat_application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin/")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getusers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @PutMapping("/updateRole")
    public ResponseEntity<String> updateUserRole(@RequestBody UpdateRoleRequestDto updateRoleRequestDto){
        try{
            return new ResponseEntity<>(userService.updateUserRole(updateRoleRequestDto),HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(userService.updateUserRole(updateRoleRequestDto),HttpStatus.OK);
        }
    }
}
