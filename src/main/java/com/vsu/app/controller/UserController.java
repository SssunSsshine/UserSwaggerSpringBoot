package com.vsu.app.controller;

import com.vsu.app.entity.User;
import com.vsu.app.request.CreateUserRequest;
import com.vsu.app.response.UserDto;
import com.vsu.app.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/users")
    public UserDto getByEmail(@RequestParam String email, @RequestParam String password) {
        return userService.getByEmailAndPassword(email, password);
    }

    @PostMapping("/users/insert")
    public UserDto insert(@RequestBody @Valid CreateUserRequest userRequest) {
        return userService.insertUser(new User(userRequest.getSurname(),
                userRequest.getName(),
                userRequest.getBirthday().toString(),
                userRequest.getEmail(),
                userRequest.getPhone(),
                userRequest.getPassword()));
    }

    @DeleteMapping("/users/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User is deleted successfully";
    }

    @PostMapping("/users/update/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody @Valid CreateUserRequest userRequest) {
        return userService.updateUser(id);
    }
}
