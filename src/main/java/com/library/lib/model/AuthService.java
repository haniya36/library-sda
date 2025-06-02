/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.lib.model;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static Map<String, LibraryUser> registeredUsers = new HashMap<>();

    static {
        // Here are the login IDs and Passwords I have set for you:
        // User 1: Username "admin", Password "admin123"
        registeredUsers.put("admin", new LibraryUser("admin", "admin123", "Admin User", "admin"));
        // User 2: Username "john", Password "password"
        registeredUsers.put("john", new LibraryUser("john", "password", "John Doe", "member"));
        // You can add more here if needed, following the same pattern.
        // For example:
        registeredUsers.put("sarah", new LibraryUser("sarah", "securepass", "Sarah Jones", "member"));

    }

    public LibraryUser authenticateUser(String username, String password) {
        LibraryUser user = registeredUsers.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean registerUser(LibraryUser newUser) {
        if (registeredUsers.containsKey(newUser.getUsername())) {
            return false; // Username already exists
        }
        registeredUsers.put(newUser.getUsername(), newUser);
        return true;
    }
}