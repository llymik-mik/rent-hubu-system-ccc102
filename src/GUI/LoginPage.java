package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class LoginPage extends JFrame {

    private static final Color FIELD_BG_COLOR = new Color(238, 238, 238);
    private static final Color BUTTON_BG_COLOR = new Color(50, 41, 38);
    private static final Color BUTTON_HOVER_COLOR = new Color(80, 65, 60);
    private static final Color BORDER_COLOR = new Color(150, 150, 150);
    private static final Color TEXT_COLOR = new Color(60, 60, 60);

    public LoginPage() {
        setTitle("IIT-SHARE Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(800, 800);
        setMinimumSize(new Dimension(400, 500));
        setResizable(true);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        JPanel loginContainer = new JPanel();
        loginContainer.setBackground(Color.WHITE);
        loginContainer.setLayout(new BoxLayout(loginContainer, BoxLayout.Y_AXIS));
        loginContainer.setBorder(new EmptyBorder(40, 60, 50, 60));

        // --- 1. LOGO ---
        try {
            URL imgURL = getClass().getResource("/logo.png.png");
            if (imgURL != null) {
                ImageIcon logoIcon = new ImageIcon(imgURL);
                Image logoImage = logoIcon.getImage().getScaledInstance(280, 150, Image.SCALE_SMOOTH);
                JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
                logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                loginContainer.add(logoLabel);
            } else {
                throw new Exception("Logo not found");
            }
        } catch (Exception e) {
            JLabel fallback = new JLabel("IIT-SHARE");
            fallback.setFont(new Font("Serif", Font.BOLD, 40));
            fallback.setForeground(BUTTON_BG_COLOR);
            fallback.setAlignmentX(Component.CENTER_ALIGNMENT);
            loginContainer.add(fallback);
        }

        loginContainer.add(Box.createVerticalStrut(50));

        // --- 2. FORM ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID NUMBER label
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel idLabel = new JLabel("ID NUMBER:");
        idLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        idLabel.setForeground(TEXT_COLOR);
        formPanel.add(idLabel, gbc);

        // ID NUMBER field
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(220, 35));
        idField.setBackground(FIELD_BG_COLOR);
        idField.setBorder(new LineBorder(BORDER_COLOR, 1));
        formPanel.add(idField, gbc);

        // Password label
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        passLabel.setForeground(TEXT_COLOR);
        formPanel.add(passLabel, gbc);

        // Password field
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(220, 35));
        passField.setBackground(FIELD_BG_COLOR);
        passField.setBorder(new LineBorder(BORDER_COLOR, 1));
        formPanel.add(passField, gbc);

        loginContainer.add(formPanel);
        loginContainer.add(Box.createVerticalStrut(40));

        // --- 3. BUTTONS ---
        JButton loginBtn = createAestheticButton("Log in");
        JButton signupBtn = createAestheticButton("Sign Up");

        signupBtn.addActionListener(e -> {
            new SignUpPage();
            dispose();
        });

        loginContainer.add(loginBtn);
        loginContainer.add(Box.createVerticalStrut(15));
        loginContainer.add(signupBtn);

        GridBagConstraints frameGbc = new GridBagConstraints();
        frameGbc.anchor = GridBagConstraints.CENTER;
        add(loginContainer, frameGbc);

        setVisible(true);
    }

    private JButton createAestheticButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw rounded background
                if (getModel().isPressed()) {
                    g2.setColor(new Color(30, 24, 22));
                } else if (getModel().isRollover()) {
                    g2.setColor(BUTTON_HOVER_COLOR);
                } else {
                    g2.setColor(BUTTON_BG_COLOR);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // Draw text
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // No border
            }
        };

        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(150, 45));
        btn.setPreferredSize(new Dimension(150, 45));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btn.repaint(); }
            @Override
            public void mouseExited(MouseEvent e) { btn.repaint(); }
        });

        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(LoginPage::new);
    }
}