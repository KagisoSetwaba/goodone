package com.socialbase.model;

public class User {
    private int userId;          // Unique identifier for the user
    private String username;     // Username of the user
    private String password;     // Password of the user
    private String email;        // Email of the user

    // Constructor
    public User(int userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and Setters
    public int getUserId() { // Fixed method name
        return userId;
    }

    public void setUserId(int userId) { // Fixed method name
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}