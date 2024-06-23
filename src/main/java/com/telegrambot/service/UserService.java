package com.telegrambot.service;


import com.telegrambot.dto.UserDTO;
import com.telegrambot.expection.UserException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserDTO user);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id) throws UserException;

    UserDTO getByUsernameAndPassword(String username, String password) throws UserException;

    UserDTO updateUser(UserDTO user);

    void deleteUser(Long id);
}
