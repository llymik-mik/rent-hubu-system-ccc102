package gui;

import main.DataStore;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final AppFrame frame;
    private JTextField idField;
    private JPasswordField passField;

    public LoginPanel(AppFrame frame) {
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
        card.setPreferredSize(new Dimension(320, 400));

        // Logo
        LogoHeader logo = createSmallLogo();
        logo.setAlignmentX(CENTER_ALIGNMENT);
        card.add(logo);
        card.add(Box.createVerticalStrut(24));

        // ID Number
        JLabel idLabel = new JLabel("ID NUMBER:");
        idLabel.setFont(Theme.FONT_LABEL);
        idLabel.setAlignmentX(LEFT_ALIGNMENT);
        idField = Theme.styledField();
        idField.setPreferredSize(new Dimension(240, 32));
        idField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        card.add(labelRow(idLabel, idField));
        card.add(Box.createVerticalStrut(14));

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(Theme.FONT_LABEL);
        passField = Theme.styledPassword();
        passField.setPreferredSize(new Dimension(240, 32));
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        card.add(labelRow(passLabel, passField));
        card.add(Box.createVerticalStrut(24));

        // Log In button
        JButton loginBtn = Theme.primaryButton("Log in");
        loginBtn.setAlignmentX(CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(180, 36));
        loginBtn.addActionListener(e -> doLogin());
        card.add(loginBtn);
        card.add(Box.createVerticalStrut(10));

        // Sign Up button
        JButton signupBtn = Theme.secondaryButton("Sign Up");
        signupBtn.setAlignmentX(CENTER_ALIGNMENT);
        signupBtn.setMaximumSize(new Dimension(180, 36));
        signupBtn.addActionListener(e -> frame.navigate(AppFrame.SIGNUP));
        card.add(signupBtn);

        // Enter key triggers login
        passField.addActionListener(e -> doLogin());
        idField.addActionListener(e -> passField.requestFocus());

        add(card);
    }

    private void doLogin() {
        String id   = idField.getText().trim();
        String pass = new String(passField.getPassword()).trim();
        if (id.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (DataStore.getInstance().login(id, pass)) {
            idField.setText("");
            passField.setText("");
            frame.navigate(AppFrame.DASHBOARD);
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid ID or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ── Helpers ─────────────────────────────────────────────────────────────────
    private JPanel labelRow(JLabel label, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBackground(Theme.WHITE);
        row.setAlignmentX(LEFT_ALIGNMENT);
        label.setPreferredSize(new Dimension(90, 28));
        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return row;
    }

    private LogoHeader createSmallLogo() {
        LogoHeader h = new LogoHeader() {
            @Override
            public Dimension getPreferredSize() { return new Dimension(240, 80); }
            @Override
            public Dimension getMaximumSize() { return new Dimension(Integer.MAX_VALUE, 80); }
        };
        h.setBorder(null);
        return h;
    }
}