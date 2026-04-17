package gui;

import main.DataStore;

import javax.swing.*;
import java.awt.*;

public class SignupPanel extends JPanel {

    private final AppFrame frame;
    private JTextField nameField, idField;
    private JPasswordField passField;

    public SignupPanel(AppFrame frame) {
        this.frame = frame;
        setBackground(Theme.WHITE);
        setLayout(new GridBagLayout());
        buildUI();
    }

    private void buildUI() {
        JPanel card = new JPanel();
        card.setBackground(Theme.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)));
        card.setPreferredSize(new Dimension(320, 420));

        // Logo
        LogoHeader logo = new LogoHeader() {
            @Override public Dimension getPreferredSize() { return new Dimension(240, 80); }
            @Override public Dimension getMaximumSize() { return new Dimension(Integer.MAX_VALUE, 80); }
        };
        logo.setBorder(null);
        logo.setAlignmentX(CENTER_ALIGNMENT);
        card.add(logo);
        card.add(Box.createVerticalStrut(20));

        // Full Name
        nameField = Theme.styledField();
        card.add(labelRow("Full Name:", nameField));
        card.add(Box.createVerticalStrut(12));

        // ID Number
        idField = Theme.styledField();
        card.add(labelRow("ID NUMBER:", idField));
        card.add(Box.createVerticalStrut(12));

        // Password
        passField = Theme.styledPassword();
        card.add(labelRow("Password:", passField));
        card.add(Box.createVerticalStrut(24));

        // Sign Up button
        JButton signupBtn = Theme.primaryButton("Sign Up");
        signupBtn.setAlignmentX(CENTER_ALIGNMENT);
        signupBtn.setMaximumSize(new Dimension(180, 36));
        signupBtn.addActionListener(e -> doRegister());
        card.add(signupBtn);
        card.add(Box.createVerticalStrut(10));

        // Back to login
        JButton backBtn = new JButton("← Back to Login");
        backBtn.setFont(Theme.FONT_SMALL);
        backBtn.setForeground(Theme.MAROON);
        backBtn.setBackground(null);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.setAlignmentX(CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> frame.navigate(AppFrame.LOGIN));
        card.add(backBtn);

        add(card);
    }

    private void doRegister() {
        String name = nameField.getText().trim();
        String id   = idField.getText().trim();
        String pass = new String(passField.getPassword()).trim();

        if (name.isEmpty() || id.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (DataStore.getInstance().register(name, id, pass)) {
            JOptionPane.showMessageDialog(frame, "Account created! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            nameField.setText(""); idField.setText(""); passField.setText("");
            frame.navigate(AppFrame.LOGIN);
        } else {
            JOptionPane.showMessageDialog(frame, "ID Number already registered.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel labelRow(String labelText, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBackground(Theme.WHITE);
        row.setAlignmentX(LEFT_ALIGNMENT);
        JLabel label = new JLabel(labelText);
        label.setFont(Theme.FONT_LABEL);
        label.setPreferredSize(new Dimension(90, 28));
        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return row;
    }
}