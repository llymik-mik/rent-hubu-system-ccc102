package main;

import gui.AppFrame;

import javax.swing.*;

/**
 * IIT-SHARE — Entry point.
 * Run this class to launch the application.
 */
public class Main {
    public static void main(String[] args) {
        // Use system look-and-feel for native widgets
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            AppFrame app = AppFrame.getInstance();
            app.setVisible(true);
        });
    }
}