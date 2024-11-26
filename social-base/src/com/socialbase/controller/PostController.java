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
    public Post createPost(Post newPost) {
        String query = "INSERT INTO posts (user_id, content, created_at) VALUES (?, ?, ?)";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, newPost.getUserId()); // Use the correct method to get user ID
            stmt.setString(2, newPost.getContent());
            stmt.setTimestamp(3, newPost.getCreatedAt());

            // Execute the insert and get the generated keys
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedPostId = generatedKeys.getInt(1);
                        newPost.setPostId(generatedPostId); // Set the generated post ID
                        System.out.println("Post created successfully with ID: " + generatedPostId);
                        return newPost; // Return the created post
                    }
                }
            } else {
                System.out.println("Post creation failed: No rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error during post creation: " + e.getMessage());
        }
        return null; // Return null if creation failed
    }

    // Retrieve all posts
    public List<Post> getAllPosts() {
        List<Post> allPosts = new ArrayList<>();
        String query = "SELECT * FROM posts";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                allPosts.add(new Post(
                    rs.getInt("post_id"),
                    rs.getInt("user_id"),
                    rs.getString("content"),
                    rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database error during retrieving posts: " + e.getMessage());
        }
        return allPosts; // Return the list of all posts
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


    public List<Post> getPostsByUserId(int userId) {
        List<Post> userPosts = new ArrayList<>();
        String query = "SELECT * FROM posts WHERE user_id = ?";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userPosts.add(new Post(
                    rs.getInt("post_id"),
                    rs.getInt("user_id"),
                    rs.getString("content"),
                    rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userPosts; // Return the list of posts
    }

    public String getUsernameByUserId(int userId) {
        User user = users.get(userId); // Assuming 'users' is a map of userId to User
        return (user != null) ? user.getUsername() : "jack";
    }
}

    