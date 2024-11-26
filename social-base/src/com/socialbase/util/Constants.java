package com.socialbase.util;

public class Constants {
    // Database connection parameters
    public static final String DB_URL = "jdbc:mysql://localhost:3306/socialbase"; // Change according to your database
    public static final String DB_USER = "root"; // Your database username
    public static final String DB_PASSWORD = "password"; // Your database password

    // Error messages
    public static final String ERROR_INVALID_CREDENTIALS = "Invalid username or password.";
    public static final String ERROR_USER_EXISTS = "Username already exists. Please choose another.";
    public static final String ERROR_REGISTER_FAILED = "Registration failed. Please try again.";
    public static final String ERROR_POST_CREATION_FAILED = "Failed to create post. Please try again.";
    public static final String ERROR_COMMENT_ADDITION_FAILED = "Failed to add comment. Please try again.";
    public static final String ERROR_REPLY_ADDITION_FAILED = "Failed to add reply. Please try again.";

    // Other constants
    public static final String APP_NAME = "SocialBase";
}