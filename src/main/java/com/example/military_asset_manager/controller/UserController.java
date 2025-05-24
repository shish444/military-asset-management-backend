package com.example.military_asset_manager.controller;

import com.example.military_asset_manager.model.Role;
import com.example.military_asset_manager.model.User;
import com.example.military_asset_manager.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Only ADMIN can create users
    @PostMapping
    public User createUser(
            @RequestHeader("X-User-Role") Role requesterRole,
            @RequestBody User user
    ) {
        if (requesterRole != Role.ADMIN) {
            throw new RuntimeException("Only admins can create users");
        }
        return userService.createUser(user);
    }

    // Only ADMIN can view all users
    @GetMapping
    public List<User> getAllUsers(@RequestHeader("X-User-Role") Role requesterRole) {
        if (requesterRole != Role.ADMIN) {
            throw new RuntimeException("Access denied");
        }
        return userService.getAllUsers();
    }

    // Public endpoint for mock authentication
    @GetMapping("/{username}")
    public User findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }
}