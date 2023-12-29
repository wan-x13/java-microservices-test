package com.wanlab.authserver.controllers;


import com.wanlab.authserver.dtos.UserDto;
import com.wanlab.authserver.entity.User;
import com.wanlab.authserver.services.UserDetailsServiceImpl;
import com.wanlab.authserver.services.UserService;
import jakarta.ws.rs.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        if(userDto.getUsername() == null || userDto.getPassword() == null ){
            throw new BadRequestException();
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        if(userDto.getUsername() == null || userDto.getPassword() == null ){
            throw new BadRequestException();
        }
        var result = userService.login(userDto);
        if(result == null){
            return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(result);
    }
}
