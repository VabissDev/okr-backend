package com.vabiss.okrbackend.controller;

import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

//    @PostMapping("register")
//    public void register(@RequestBody UserDto userDto){
//        userService.addUser(userDto);
//    }


    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }
}
