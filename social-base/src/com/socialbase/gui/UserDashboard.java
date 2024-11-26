package com.socialbase.gui;

import javax.swing.*;
import com.socialbase.controller.PostController;
import com.socialbase.model.Post;
import com.socialbase.model.User;

import java.awt.*;
import java.util.List;

public class UserDashboard extends JFrame {
    private JList<String> postList;
    private DefaultListModel<String> listModel; // Store the list model for easy access
    private User loggedInUser ;
    private PostController postController;

    public UserDashboard(User loggedInUser , PostController postController) {
        this.loggedInUser  = loggedInUser ; // Initialize the logged-in user
        this.postController = postController; // Initialize the PostController

        setTitle("User  Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        postList = new JList<>(listModel); // Initialize the JList with the model

        // Load posts for the logged-in user
        loadPosts();

        JButton createPostButton = new JButton("Create New Post");
        JButton logoutButton = new JButton("Logout"); // Logout button

        // Action listener for creating a new post
        createPostButton.addActionListener(e -> {
            PostCreationFrame postCreationFrame = new PostCreationFrame(loggedInUser .getUserId(), postController, this);
            postCreationFrame.setVisible(true);
        });

        // Action listener for the logout button
        logoutButton.addActionListener(e -> {
            // Close the current dashboard and return to the login screen
            dispose(); // Close the dashboard
            LoginFrame loginFrame = new LoginFrame(postController); // Create a new login frame
            loginFrame.setVisible(true); // Show the login frame
        });

        // Adding components to the frame
        add(new JScrollPane(postList), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(); // Panel for buttons
        buttonPanel.add(createPostButton);
        buttonPanel.add(logoutButton); // Add logout button to the panel
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Load posts for the logged-in user
    public void loadPosts() {
        List<Post> userPosts = postController.getPostsByUserId(loggedInUser .getUserId()); // Retrieve posts for the logged-in user
        listModel.clear(); // Clear existing posts in the model

        for (Post post : userPosts) {
            listModel.addElement(post.getContent()); // Assuming Post has a getContent() method
        }
    }
    
}