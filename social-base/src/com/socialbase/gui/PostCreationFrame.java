package com.socialbase.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.socialbase.controller.PostController;

public class PostCreationFrame extends JFrame {
    private JTextArea postContentArea;
    private int userId; // ID of the user creating the post
    private PostController postController; // Reference to PostController

    public PostCreationFrame(int userId, PostController postController) {
        this.userId = userId; // Store the user ID
        this.postController = postController; // Store the PostController instance

        setTitle("Create Post");
        setSize(400 , 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        postContentArea = new JTextArea();
        JButton submitButton = new JButton("Submit");

        // Action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = postContentArea.getText();
                try {
                    // Call the PostController to create a new post
                    postController.createPost(userId, content);
                    JOptionPane.showMessageDialog(PostCreationFrame.this, "Post created successfully!");
                    dispose(); // Close the frame after successful creation
                } catch (IllegalArgumentException ex) {
                    // Handle the case where post creation fails (e.g., empty content)
                    JOptionPane.showMessageDialog(PostCreationFrame.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(new JScrollPane(postContentArea), BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }
}