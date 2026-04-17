package GUI;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame { // Main now IS the window

    // Static variables so other pages can still call Main.navigateTo()
    public static CardLayout cardLayout = new CardLayout();
    public static JPanel mainPanel = new JPanel(cardLayout);
    public static String loggedInStudent = "";
    private static Main instance; // Reference to the actual window

    public Main() {
        // 1. Setup the Window
        setTitle("IIT-SHARE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 720);
        setMinimumSize(new Dimension(750, 550));
        setLocationRelativeTo(null);

        // 2. Setup the CardLayout Panel
        mainPanel.add(new LoginPage(), "LOGIN");
        mainPanel.add(new SignUpPage(), "SIGNUP");

        // 3. Add to the JFrame and show
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");

        instance = this; // Store the instance for navigation
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start the application
        SwingUtilities.invokeLater(() -> new Main());
    }

    // --- Navigation Methods ---

    public static void navigateTo(String pageName) {
        cardLayout.show(mainPanel, pageName);
    }

    public static void navigateToDashboard(String studentName) {
        loggedInStudent = studentName;
        mainPanel.add(new DashboardPage(studentName), "DASHBOARD");
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    public static void navigateToPostItem() {
        mainPanel.add(new PostItemPage(loggedInStudent), "POST_ITEM");
        cardLayout.show(mainPanel, "POST_ITEM");
    }

    public static void navigateToRequestItem(String name, String owner, String status, String tags) {
        mainPanel.add(new RequestItemPage(loggedInStudent, name, owner, status, tags), "REQUEST_ITEM");
        cardLayout.show(mainPanel, "REQUEST_ITEM");
    }
}