package com.wanlab.authserver.services;


import com.wanlab.authserver.dtos.LoginDto;
import com.wanlab.authserver.dtos.UserDto;
import com.wanlab.authserver.entity.Role;
import com.wanlab.authserver.entity.User;
import com.wanlab.authserver.exceptions.ConflictException;
import com.wanlab.authserver.repository.RoleRepository;
import com.wanlab.authserver.repository.UserRepository;
import com.wanlab.authserver.security.utils.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public User save(User user) {
        if(userRepository.findAll().stream()
                .anyMatch(it->it.getUsername().equals(user.getUsername()))){
            throw new ConflictException("User already exists");
        }
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);
        user.setRoles(List.of(role));
        return userRepository.save(user);
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public LoginDto login(UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(),
                        userDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return  new LoginDto(userDetails.getUsername(), jwt);
    }

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
