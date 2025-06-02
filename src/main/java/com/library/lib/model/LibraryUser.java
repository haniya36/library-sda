package com.library.lib.model;

public class LibraryUser {
    private String username;
    private String password; 
    private String fullName;
    private String role; 

    public LibraryUser(String username, String password, String fullName, String role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public LibraryUser(String admin, String adminpass, String librarian) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // REMOVE THIS INCORRECT CONSTRUCTOR
    // public LibraryUser(String admin, String password123, String admin0) {
    //     throw new UnsupportedOperationException("Not supported yet."); 
    // }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "LibraryUser{" +
                "username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}