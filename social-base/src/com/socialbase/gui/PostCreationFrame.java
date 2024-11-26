package com.socialbase.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.socialbase.controller.PostController;
import com.socialbase.model.Post; // Ensure you import the Post model
import java.sql.Timestamp;

public class PostCreationFrame extends JFrame {
    private JTextArea postContentArea;
    private int userId; // ID of the user creating the post
    private PostController postController; // Reference to PostController
    private UserDashboard userDashboard; // Reference to UserDashboard

    public PostCreationFrame(int userId, PostController postController, UserDashboard userDashboard) {
        this.userId = userId; // Store the user ID
        this.postController = postController; // Store the PostController instance
        this.userDashboard = userDashboard; // Store the UserDashboard reference

        setTitle("Create Post");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        postContentArea = new JTextArea();
        JButton submitButton = new JButton("Submit");

        // Action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = postContentArea.getText().trim(); // Trim whitespace
                if (content.isEmpty()) {
                    JOptionPane.showMessageDialog(PostCreationFrame.this, "Post content cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit if content is empty
                }

                // Create a new Post object
                Post newPost = new Post(0, userId, content, new Timestamp(System.currentTimeMillis())); // ID is 0 for new posts
                try {
                    // Call the PostController to create a new post
                    postController.createPost(newPost);
                    JOptionPane.showMessageDialog(PostCreationFrame.this, "Post created successfully!");
                    userDashboard.loadPosts(); // Refresh the posts in UserDashboard
                    dispose(); // Close the frame after successful creation
                } catch (Exception ex) {
                    // Handle any exceptions that occur during post creation
                    JOptionPane.showMessageDialog(PostCreationFrame.this, "Failed to create post: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(new JScrollPane(postContentArea), BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }
}