package com.library.lib.dao;

import com.library.lib.model.LibraryUser;

public interface UserDao {
    LibraryUser getUserByUsername(String username);
    boolean addUser(LibraryUser user);
    // Add other methods like update, delete, getAll if needed for user management
}