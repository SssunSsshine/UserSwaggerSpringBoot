package com.vsu.app.controller;

import com.vsu.app.entity.User;
import com.vsu.app.request.CreateUserRequest;
import com.vsu.app.response.UserDto;
import com.vsu.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        try {
            return userService.getById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/users")
    public UserDto getByEmail(@RequestParam String email, @RequestParam String password) {

            return userService.getByEmailAndPassword(email, password);
    }

    @PostMapping("/users/insert")
    public ResponseEntity<Object> insert(@RequestBody @Valid CreateUserRequest userRequest) {

            UserDto user = userService.insertUser(new User(userRequest.getSurname(),
                    userRequest.getName(),
                    userRequest.getBirthday().toString(),
                    userRequest.getEmail(),
                    userRequest.getPhone(),
                    userRequest.getPassword()));

            if (user != null)
                return new ResponseEntity<>(user, HttpStatus.OK);
            return new ResponseEntity<>("User is not inserted", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {

            Long idL = Long.parseLong(id);
            if (userService.deleteUser(idL))
                return new ResponseEntity<>("User is deleted successfully", HttpStatus.OK);
            return new ResponseEntity<>("User is not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/users/update/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody @Valid CreateUserRequest userRequest) {
            Long idL = Long.parseLong(id);
            UserDto user = userService.getById(idL);

            if (user == null) {
                return new ResponseEntity<>("User is not found", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            User updatedUser = new User(user.getId(), userRequest.getSurname(), userRequest.getName(), userRequest.getBirthday().toString(), userRequest.getEmail(), userRequest.getPhone(), userRequest.getPassword());

            UserDto updUser = userService.updateUser(updatedUser);
            if (updUser != null)
                return new ResponseEntity<>(updUser, HttpStatus.OK);
            return new ResponseEntity<>("User is not updated", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
