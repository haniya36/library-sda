/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.lib.model;

// src/com/librarymanagementsystem/model/User.java

import java.io.Serializable; // Necessary for UserDaoImpl's file serialization

public class User implements Serializable { // Implement Serializable
    private String fullName;
    private String email;
    private String username;
    private String password;

    // Constructor with all parameters
    public User(String fullName, String email, String username, String password) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String librarian) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // --- Getter methods ---
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // --- Setters (optional, add if user profile updates are allowed) ---
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; } // Be careful with direct password setting in real apps

    @Override
    public String toString() {
        return "User{" +
               "fullName='" + fullName + '\'' +
               ", email='" + email + '\'' +
               ", username='" + username + '\'' +
               '}';
    }
}