package com.socialbase.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommentFrame extends JFrame {
    private JTextArea commentArea;
    private JList<String> commentList;

    public CommentFrame() {
        setTitle("Comments");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Correctly sets the frame to the center of the screen
        setLayout(new BorderLayout());
    
        commentArea = new JTextArea();
        JButton submitButton = new JButton("Add Comment");
        DefaultListModel<String> listModel = new DefaultListModel<>();
        commentList = new JList<>(listModel);
    
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comment = commentArea.getText();
                // Call CommentController to add the comment
                // If successful, update the comment list
                listModel.addElement(comment);
                commentArea.setText(""); // Clear the text area
            }
        });
    
        add(new JScrollPane(commentList), BorderLayout.CENTER);
        add(commentArea, BorderLayout.SOUTH);
        add(submitButton, BorderLayout.NORTH);
    }
}