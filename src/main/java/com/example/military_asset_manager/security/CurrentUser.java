package com.example.military_asset_manager.security;

import com.example.military_asset_manager.model.User;

public class CurrentUser {
    // Temporary: Store current user info manually in controller/service for now.
    private static User currentUser;

    public static void set(User user) {
        currentUser = user;
    }

    public static User get() {
        return currentUser;
    }
}
