package com.telegrambot.service.Impl;


import com.telegrambot.dto.UserDTO;
import com.telegrambot.entity.User;
import com.telegrambot.expection.UserException;
import com.telegrambot.mapper.UserMapper;
import com.telegrambot.repository.UserRepository;
import com.telegrambot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO createUser(UserDTO user) {
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO getByUsernameAndPassword(String username, String password) throws UserException {
        return userMapper.toDTO(
                userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new UserException("User not found")));
    }

    @Override
    public UserDTO getUserById(Long id) throws UserException {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public UserDTO updateUser(UserDTO user) {
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}