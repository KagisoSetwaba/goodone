package com.socialbase.gui;

import javax.swing.*;
import java.awt.*;

public class SocialBaseGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(null);
            loginFrame.setVisible(true);
        });
    }
}