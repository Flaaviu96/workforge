package dev.workforge.app.WorkForge.controllers;

import dev.workforge.app.WorkForge.dto.UserViewDTO;
import dev.workforge.app.WorkForge.service.user_permission.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserViewDTO> searchUsers(@RequestParam(value = "search", required = false) String search) {
        return userService.getUsersByPrefix(search);
    }
}
