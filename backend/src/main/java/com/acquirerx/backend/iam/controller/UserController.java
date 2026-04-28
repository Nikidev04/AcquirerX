package com.acquirerx.backend.iam.controller;

import com.acquirerx.backend.iam.dto.*;
import com.acquirerx.backend.iam.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/iam/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService us) {
        this.userService = us;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Integer id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDTO create(@RequestBody CreateUserDTO dto) {
        return userService.createUser(dto);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable("id") Integer id, @RequestBody UpdateUserDTO dto) {
        return userService.updateUser(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "User deleted";
    }
}
