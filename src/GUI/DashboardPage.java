package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class DashboardPage extends JFrame {

    private static final Color BUTTON_BG_COLOR = new Color(50, 41, 38);
    private static final Color BUTTON_HOVER_COLOR = new Color(80, 65, 60);
    private static final Color FIELD_BG_COLOR = new Color(220, 220, 220);
    private static final Color TEXT_COLOR = new Color(60, 60, 60);
    private static final Color CARD_BORDER_COLOR = new Color(180, 60, 60);

    public DashboardPage() {
        setTitle("IIT-SHARE Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setMinimumSize(new Dimension(700, 500));
        setResizable(true);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // ============================================================
        // TOP PANEL — Logo + Search Bar
        // ============================================================
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        // Logo
        JLabel logoLabel;
        try {
            URL imgURL = getClass().getResource("/logo.png.png");
            if (imgURL == null) throw new Exception("Logo not found");
            ImageIcon logoIcon = new ImageIcon(imgURL);
            Image logoImage = logoIcon.getImage().getScaledInstance(110, 60, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(logoImage));
        } catch (Exception e) {
            logoLabel = new JLabel("IIT-SHARE");
            logoLabel.setFont(new Font("Serif", Font.BOLD, 22));
            logoLabel.setForeground(BUTTON_BG_COLOR);
        }
        topPanel.add(logoLabel, BorderLayout.WEST);

        // Search bar (top right)
        JTextField topSearchField = new JTextField();
        topSearchField.setPreferredSize(new Dimension(500, 45));
        topSearchField.setBackground(FIELD_BG_COLOR);
        topSearchField.setBorder(new LineBorder(new Color(180, 180, 180), 1));
        topSearchField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel searchWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        searchWrapper.setBackground(Color.WHITE);
        searchWrapper.add(topSearchField);
        topPanel.add(searchWrapper, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // ============================================================
        // MAIN CONTENT PANEL
        // ============================================================
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(0, 15, 15, 15));

        // ---- ROW 1: Name + Post Item + History ----
        JPanel nameRow = new JPanel(new BorderLayout(10, 0));
        nameRow.setBackground(Color.WHITE);
        nameRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JTextField nameField = new JTextField("Name:  Student ABC");
        nameField.setEditable(false);
        nameField.setBackground(FIELD_BG_COLOR);
        nameField.setBorder(new LineBorder(new Color(180, 180, 180), 1));
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField.setForeground(TEXT_COLOR);

        JPanel nameBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        nameBtnPanel.setBackground(Color.WHITE);
        JButton postItemBtn = createAestheticButton("Post Item");
        JButton historyBtn = createAestheticButton("History");
        nameBtnPanel.add(postItemBtn);
        nameBtnPanel.add(historyBtn);

        nameRow.add(nameField, BorderLayout.CENTER);
        nameRow.add(nameBtnPanel, BorderLayout.EAST);
        mainPanel.add(nameRow);
        mainPanel.add(Box.createVerticalStrut(12));

        // ---- ROW 2: Search Items Row ----
        JPanel searchItemsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchItemsRow.setBackground(Color.WHITE);
        searchItemsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel searchItemsLabel = new JLabel("Search items:");
        searchItemsLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        searchItemsLabel.setForeground(TEXT_COLOR);

        JTextField itemSearchField = new JTextField("Calculator");
        itemSearchField.setPreferredSize(new Dimension(120, 30));
        itemSearchField.setBackground(FIELD_BG_COLOR);
        itemSearchField.setBorder(new LineBorder(new Color(180, 180, 180), 1));
        itemSearchField.setFont(new Font("SansSerif", Font.PLAIN, 13));

        String[] colleges = {"College of Computer Science", "College of Engineering", "College of Arts"};
        JComboBox<String> collegeDropdown = new JComboBox<>(colleges);
        collegeDropdown.setPreferredSize(new Dimension(210, 30));
        collegeDropdown.setBackground(FIELD_BG_COLOR);
        collegeDropdown.setFont(new Font("SansSerif", Font.PLAIN, 13));

        String[] programs = {"Information Systems", "Computer Science", "Information Technology"};
        JComboBox<String> programDropdown = new JComboBox<>(programs);
        programDropdown.setPreferredSize(new Dimension(180, 30));
        programDropdown.setBackground(FIELD_BG_COLOR);
        programDropdown.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JButton searchBtn = createAestheticButton("Search");
        searchBtn.setMaximumSize(new Dimension(90, 32));
        searchBtn.setPreferredSize(new Dimension(90, 32));

        JButton clearBtn = new JButton("Clear filters");
        clearBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        clearBtn.setForeground(TEXT_COLOR);
        clearBtn.setBackground(Color.WHITE);
        clearBtn.setBorderPainted(false);
        clearBtn.setContentAreaFilled(false);
        clearBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { clearBtn.setForeground(Color.RED); }
            public void mouseExited(MouseEvent e) { clearBtn.setForeground(TEXT_COLOR); }
        });

        searchItemsRow.add(searchItemsLabel);
        searchItemsRow.add(itemSearchField);
        searchItemsRow.add(collegeDropdown);
        searchItemsRow.add(programDropdown);
        searchItemsRow.add(searchBtn);
        searchItemsRow.add(clearBtn);
        mainPanel.add(searchItemsRow);
        mainPanel.add(Box.createVerticalStrut(20));

        // ---- AVAILABLE ITEMS LABEL ----
        JLabel availableLabel = new JLabel("AVAILABLE ITEMS:");
        availableLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        availableLabel.setForeground(TEXT_COLOR);
        availableLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(availableLabel);
        mainPanel.add(Box.createVerticalStrut(15));

        // ---- ITEMS GRID ----
        JPanel itemsGrid = new JPanel(new GridLayout(1, 3, 20, 0));
        itemsGrid.setBackground(Color.WHITE);

        // Sample items data
        String[][] items = {
                {"Lab Gown", "Student A", "Available", "#CSM #BIO #lab"},
                {"Calculator", "Student B", "Available", "#COE #Calc #Math"},
                {"Tripod", "Student C", "Available", "#CED #Film #Event"}
        };

        for (String[] item : items) {
            itemsGrid.add(createItemCard(item[0], item[1], item[2], item[3]));
        }

        // Make grid scrollable
        JScrollPane scrollPane = new JScrollPane(itemsGrid);
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(scrollPane);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // ============================================================
    // ITEM CARD
    // ============================================================
    private JPanel createItemCard(String itemName, String owner, String status, String tags) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(CARD_BORDER_COLOR, 1),
                new EmptyBorder(10, 10, 10, 10)
        ));

        // Image placeholder box
        JPanel imagePlaceholder = new JPanel();
        imagePlaceholder.setBackground(FIELD_BG_COLOR);
        imagePlaceholder.setPreferredSize(new Dimension(180, 160));
        imagePlaceholder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        imagePlaceholder.setBorder(new LineBorder(new Color(180, 180, 180), 1));

        JLabel imgLabel = new JLabel("[No Image]");
        imgLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        imgLabel.setForeground(new Color(150, 150, 150));
        imagePlaceholder.add(imgLabel);
        card.add(imagePlaceholder);

        card.add(Box.createVerticalStrut(10));

        // Item details
        JLabel itemLabel = new JLabel("<html><b>Item:</b> " + itemName + "<br>"
                + "<b>Owner:</b> " + owner + "<br>"
                + "<b>Status:</b> " + status + "<br>"
                + "<font color='gray'>" + tags + "</font></html>");
        itemLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        itemLabel.setForeground(TEXT_COLOR);
        itemLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(itemLabel);

        card.add(Box.createVerticalStrut(10));

        // Request button
        JButton requestBtn = createAestheticButton("Request");
        requestBtn.setMaximumSize(new Dimension(110, 35));
        requestBtn.setPreferredSize(new Dimension(110, 35));
        requestBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(requestBtn);

        return card;
    }

    // ============================================================
    // AESTHETIC BUTTON
    // ============================================================
    private JButton createAestheticButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(new Color(30, 24, 22));
                } else if (getModel().isRollover()) {
                    g2.setColor(BUTTON_HOVER_COLOR);
                } else {
                    g2.setColor(BUTTON_BG_COLOR);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {}
        };

        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(150, 45));
        btn.setPreferredSize(new Dimension(150, 45));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.repaint(); }
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
        SwingUtilities.invokeLater(DashboardPage::new);
    }
}