package com.library.lib.service; // Changed package to 'service' as it's a service class

import com.library.lib.model.LibraryUser; // Corrected import to the specific LibraryUser class
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private static Map<String, LibraryUser> registeredUsers = new HashMap<>();

    static {
        // Add some dummy users for testing
        // Syntax for LibraryUser: new LibraryUser(username, password, fullName, role)
        registeredUsers.put("admin", new LibraryUser("admin", "admin123", "Admin User", "admin"));
        registeredUsers.put("john", new LibraryUser("john", "password", "John Doe", "member"));
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