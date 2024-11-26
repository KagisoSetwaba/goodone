package com.socialbase;

import com.socialbase.controller.PostController;
import com.socialbase.gui.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set the look and feel (optional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create an instance of PostController
        PostController postController = new PostController();

        // Launch the LoginFrame
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(postController); // Pass the PostController instance
            loginFrame.setVisible(true);
        });
    }
}