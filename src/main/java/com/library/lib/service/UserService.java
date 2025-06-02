package com.library.lib.service;

import com.library.lib.dao.UserDao;
import com.library.lib.dao.UserDaoImpl;
import com.library.lib.model.LibraryUser; // Correct import
import com.library.lib.model.User;

public class UserService {
    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDaoImpl(); // Instantiate the implementation
    }

    // If you want to allow dependency injection for testing or flexibility:
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Registers a new user (librarian/admin) in the system.
     * @param user The LibraryUser object to be registered.
     * @return true if the user is registered successfully, false if username is already taken.
     */
    public boolean registerUser(LibraryUser user) {
        // Add any business logic/validation here before saving
        return userDao.addUser(user);
    }

    /**
     * Authenticates a user based on username and password.
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @return The LibraryUser object if login is successful, null otherwise.
     */
    public LibraryUser loginUser(String username, String password) {
        LibraryUser user = userDao.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Login successful
        }
        return null; // Login failed
    }

    /**
     * Retrieves a user by their username.
     * @param username The username to search for.
     * @return The LibraryUser object if found, null otherwise.
     */
    public LibraryUser getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public boolean registerUser(User newUser) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}