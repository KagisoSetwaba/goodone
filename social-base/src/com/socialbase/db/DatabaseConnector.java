package com.socialbase.db;

import com.socialbase.model.User;
import com.socialbase.model.Post;
import com.socialbase.model.Comment;
import com.socialbase.model.Reply;

import java.sql.*;

public class DatabaseConnector {
    // Database connection parameters
    private final String url = "jdbc:mysql://localhost:3306/socialbase"; // Change according to your database
    private final String user = "root"; // Your database username
    private final String password = ""; // Your database password

    // Method to get a new database connection
    public Connection getConnection() throws SQLException {
        System.out.println("Opening new database connection...");
        return DriverManager.getConnection(url, user, password);
    }

    // Check if a username already exists
    public User readUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if user not found
    }

    // CRUD Operations for User
    public void createUser (User user) {
        // Check if the username already exists
        if (readUserByUsername(user.getUsername()) != null) {
            System.out.println("Username already exists: " + user.getUsername());
            return; // Username already exists, do not proceed
        }
        
        String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Hash the password before storing
            stmt.setString(3, user.getEmail());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User  registered successfully: " + user.getUsername());
            } else {
                System.out.println("User  registration failed: No rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public User readUser (int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser (User user) {
        String query = "UPDATE users SET username = ?, password = ?, email = ? WHERE user_id = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setInt(4, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser (int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CRUD Operations for Post
    public void createPost(Post post) {
        String query = "INSERT INTO posts (user_id, content, created_at) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, post.getUserId());
            stmt.setString(2, post.getContent());
            stmt.setTimestamp(3, post.getCreatedAt());
    
            // Execute the insert and get the number of affected rows
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Retrieve the generated keys (post_id)
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedPostId = generatedKeys.getInt(1);
                        post.setPostId(generatedPostId); // Set the generated post ID
                        System.out.println("Post created successfully with ID: " + generatedPostId);
    
                        // Verify the post is stored in the database
                        Post createdPost = readPost(generatedPostId); // Retrieve the post by ID
                        if (createdPost != null) {
                            System.out.println("Post verified successfully: " + createdPost.getContent());
                        } else {
                            System.out.println("Post verification failed: Post not found in the database.");
                        }
                    }
                }
            } else {
                System.out.println("Post creation failed: No rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Post readPost(int postId) {
        String query = "SELECT * FROM posts WHERE post_id = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Post(
                    rs.getInt("post_id"),
                    rs.getInt("user_id"),
                    rs.getString("content"),
                    rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePost(Post post) {
        String query = "UPDATE posts SET content = ? WHERE post_id = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, post.getContent());
            stmt.setInt(2, post.getPostId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePost(int postId) {
        String query = "DELETE FROM posts WHERE post_id = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, postId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CRUD Operations for Comment
    public void createComment(Comment comment) {
        String query = "INSERT INTO comments (post_id, user_id, content, created_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, comment.getPostId());
            stmt.setInt(2, comment.getUserId());
            stmt.setString(3, comment.getContent());
            stmt.setTimestamp(4, comment.getCreatedAt());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Comment readComment(int commentId) {
        String query = "SELECT * FROM comments WHERE comment_id = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, commentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Comment(
                    rs.getInt("comment_id"),
                    rs.getInt("post_id"),
                    rs.getInt("user_id"),
                    rs.getString("content"),
                    rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateComment(Comment comment) {
        String query = "UPDATE comments SET content = ? WHERE comment_id = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, comment.getContent());
            stmt.setInt(2, comment.getCommentId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteComment(int commentId) {
        String query = "DELETE FROM comments WHERE comment_id = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, commentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CRUD Operations for Reply
    public void createReply(Reply reply) {
        String query = "INSERT INTO replies (comment_id, user_id, content, created_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, reply.getCommentId());
            stmt.setInt(2, reply.getUserId());
            stmt.setString(3, reply.getContent());
            stmt.setTimestamp(4, reply.getCreatedAt());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Reply readReply(int replyId) {
        String query = "SELECT * FROM replies WHERE reply_id = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, replyId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Reply(
                    rs.getInt("reply_id"),
                    rs.getInt("comment_id"),
                    rs.getInt("user_id"),
                    rs.getString("content"),
                    rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateReply(Reply reply) {
        String query = "UPDATE replies SET content = ? WHERE reply_id = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, reply.getContent());
            stmt.setInt(2, reply.getReplyId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteReply(int replyId) {
        String query = "DELETE FROM replies WHERE reply_id = ?";
        try (Connection connection = getConnection(); // Get a new connection
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, replyId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}