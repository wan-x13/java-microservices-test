package com.wanlab.authserver.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanlab.authserver.config.EmailPayload;
import com.wanlab.authserver.dtos.LoginRequest;
import com.wanlab.authserver.dtos.UserDto;
import com.wanlab.authserver.entity.User;
import com.wanlab.authserver.services.UserService;
import jakarta.ws.rs.BadRequestException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private String QUEUE_NAME = "mail_queue";
    private final RabbitTemplate rabbitTemplate;
    private final UserService userService;

    public UserController(RabbitTemplate rabbitTemplate, UserService userService) {
        this.rabbitTemplate = rabbitTemplate;
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) throws JsonProcessingException {
        if(userDto.getUsername() == null || userDto.getPassword() == null ){
            throw new BadRequestException();
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        userService.save(user);
        EmailPayload emailPayload = new EmailPayload(user.getUsername(), user.getEmail());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(emailPayload);
        System.out.println(json);
        rabbitTemplate.convertAndSend(QUEUE_NAME, json);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest userDto) {
        if(userDto.getUsername() == null || userDto.getPassword() == null ){
            throw new BadRequestException();
        }
        var result = userService.login(userDto);
        System.out.println(result);
        if(result == null){
            return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(result);
    }
}
