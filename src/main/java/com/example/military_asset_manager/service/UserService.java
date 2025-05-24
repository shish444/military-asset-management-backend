package com.example.military_asset_manager.service;

import com.example.military_asset_manager.model.Role;
import com.example.military_asset_manager.model.User;
import com.example.military_asset_manager.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a user with role validation
    public User createUser(User user) {
        // Ensure Base Commanders have a base assigned
        if (user.getRole() == Role.BASE_COMMANDER && (user.getBase() == null || user.getBase().isBlank())) {
            throw new RuntimeException("Base Commander must be assigned to a base");
        }
        return userRepository.save(user);
    }

    // Get all users (for admins)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find user by username (for mocking auth)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}