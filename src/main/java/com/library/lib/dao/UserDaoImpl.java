package com.library.lib.dao;

import com.library.lib.model.LibraryUser;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private Map<String, LibraryUser> users = new HashMap<>();

    public UserDaoImpl() {
        // Dummy data for users (e.g., an admin user)
        users.put("admin", new LibraryUser("admin", "adminpass", "LIBRARIAN"));
        users.put("testuser", new LibraryUser("testuser", "password123", "MEMBER"));
    }

    @Override
    public boolean addUser(LibraryUser user) {
        if (users.containsKey(user.getUsername())) {
            System.out.println("DEBUG: User with username " + user.getUsername() + " already exists. Cannot add.");
            return false;
        }
        users.put(user.getUsername(), user);
        System.out.println("DEBUG: Added user: " + user.getUsername());
        return true;
    }

    @Override
    public LibraryUser getUserByUsername(String username) {
        return users.get(username);
    }

    public boolean updateUser(LibraryUser user) {
        if (users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user); // Overwrites existing user
            System.out.println("DEBUG: Updated user: " + user.getUsername());
            return true;
        }
        System.out.println("DEBUG: User with username " + user.getUsername() + " not found for update.");
        return false;
    }

    public boolean deleteUser(String username) {
        if (users.containsKey(username)) {
            users.remove(username);
            System.out.println("DEBUG: Removed user: " + username);
            return true;
        }
        System.out.println("DEBUG: User with username " + username + " not found for deletion.");
        return false;
    }

    public List<LibraryUser> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}