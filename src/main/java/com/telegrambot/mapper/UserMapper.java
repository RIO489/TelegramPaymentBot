package com.telegrambot.mapper;

import com.telegrambot.dto.UserDTO;
import com.telegrambot.entity.User;
import com.telegrambot.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public  UserDTO toDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole().toString());
       // dto.setPassword("empty");
        return dto;
    }

    public  User toEntity(UserDTO dto){
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(UserRole.valueOf(dto.getRole()));
        return user;
    }
}
