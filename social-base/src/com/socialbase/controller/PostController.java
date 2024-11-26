package com.socialbase.controller;

import com.socialbase.db.DatabaseConnector;
import com.socialbase.model.Post;
import com.socialbase.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostController {
    private Map<Integer, Post> posts = new HashMap<>(); // Simulating a database with a HashMap
    private Map<Integer, User> users = new HashMap<>(); // Simulating a database with a HashMap for users
    private int postIdCounter = 1; // To generate unique post IDs
    private int userIdCounter = 1; // To generate unique user IDs
    private DatabaseConnector databaseConnector;
    
        // Create a new post
        public Post createPost(int userId, String content) {
            Post newPost = new Post(postIdCounter++, userId, content, new Timestamp(System.currentTimeMillis()));
            posts.put(newPost.getPostId(), newPost);
            return newPost;
        }
    
    
        // Retrieve all posts
        public List<Post> getAllPosts() {
            return new ArrayList<>(posts.values());
        }
    
        // Implement the loginUser  method
        public User registerUser (String username, String password, String email) {
            // Check if the username already exists
            for (User  user : users.values()) {
                if (user.getUsername().equals(username)) {
                    return null; // Username already exists
                }
            }
        
            // Create a new user
            User newUser  = new User(userIdCounter++, username, password, email);
            users.put(newUser .getUserId(), newUser ); // Store the new user in the users map
            return newUser ; // Return the newly created user
        }
       
        public List<Post> getPostsByUserId(int userId) { // Corrected method name
            List<Post> userPosts = new ArrayList<>();
            for (Post post : posts.values()) {
                if (post.getUserId() == userId) { // Corrected method call
                    userPosts.add(post);
                }
            }
            return userPosts; // Return the list of posts for the specified user
        }
        
        public PostController() {
            this.databaseConnector = new DatabaseConnector();
            this.posts = new HashMap<>(); // Initialize the posts map
            this.users = new HashMap<>(); // Initialize the users map, if needed
        }

    // Implement the loginUser  method
    public User loginUser (String username, String password) {
        User user = null;
        String query = "SELECT * FROM users WHERE username = ? AND password = ?"; // Password should be hashed

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Hash the password before checking (replace with your hashing method)
            String hashedPassword = hashPassword(password); // Ensure you implement this method

            statement.setString(1, username);
            statement.setString(2, hashedPassword);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userUsername = resultSet.getString("username");
                String email = resultSet.getString("email"); // Assuming you have an email field

                user = new User(userId, userUsername, hashedPassword, email);
                System.out.println("Login successful for user : " + userUsername);
            } else {
                System.out.println("Login failed for username: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error during login: " + e.getMessage());
        }

        return user; // Returns null if login failed
    }

    // Example hashing method (implement your own hashing logic)
    private String hashPassword(String password) {
        // Implement your password hashing logic here (e.g., using BCrypt)
        return password; // Placeholder, replace with actual hashed password
    }
}

    