package com.socialbase.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.socialbase.controller.PostController;
import com.socialbase.model.User;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private PostController postController; // Reference to PostController

    public LoginFrame(PostController postController) {
        this.postController = postController; // Store the PostController instance

        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login logic here
                String username = usernameField.getText().trim(); // Trimmed username
                String password = new String(passwordField.getPassword()).trim(); // Trimmed password

                // Debugging output
                System.out.println("Attempting to log in with Username: " + username + " and Password: " + password);

                // Call UserController to validate login
                User loggedInUser  = postController.loginUser (username, password); // Assuming you have this method
                if (loggedInUser  != null) {
                    // If successful, open UserDashboard
                    UserDashboard userDashboard = new UserDashboard(loggedInUser , postController);
                    userDashboard.setVisible(true);
                    dispose(); // Close login frame
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(e -> {
            RegistrationFrame registrationFrame = new RegistrationFrame(postController);
            registrationFrame.setVisible(true);
            dispose(); // Close login frame
        });

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(registerButton);
    }
}