package com.vsu.app.service;

import com.vsu.app.entity.User;
import com.vsu.app.response.UserDto;
import com.vsu.app.exception.DBException;
import com.vsu.app.exception.ValidationException;
import com.vsu.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    public static final int COUNT_UPDATED_ROWS = 1;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getById(Long id) {
        User user = userRepository.getById(id);
        return fromUserToUserDto(user);
    }

    public UserDto getByEmailAndPassword(String email, String password) {
        User user = userRepository.getByEmail(email);
        if (user == null) {
            LOGGER.log(Level.INFO, "User with login {0} is not exist", email);
            throw new ValidationException("User is not found");
        } else {
            if (user.getPassword().equals(password))
                return fromUserToUserDto(user);
        }
        LOGGER.log(Level.WARNING, "Something wrong with input data");
        throw new ValidationException("Bad credentials");
    }

    public UserDto insertUser(User user) {
        if (userRepository.insert(user) < COUNT_UPDATED_ROWS) {
            LOGGER.log(Level.WARNING, "User {0} is not inserted", user);
            return null;
        }
        return fromUserToUserDto(user);

    }

    public boolean deleteUser(Long id) {
        return userRepository.deleteById(id) >= COUNT_UPDATED_ROWS;
    }

    public UserDto updateUser(User user) {
        if (userRepository.updateById(user) < COUNT_UPDATED_ROWS) {
            LOGGER.log(Level.WARNING, "User {0} is not updated", user);
            return null;
        }
        return fromUserToUserDto(user);
    }

    private UserDto fromUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .surname(user.getSurname())
                .name(user.getName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
