package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class DashboardPage extends JFrame {

    private static final Color PRIMARY       = new Color(50, 41, 38);
    private static final Color PRIMARY_HOVER = new Color(75, 62, 57);
    private static final Color ACCENT        = new Color(180, 55, 45);
    private static final Color BG            = new Color(245, 244, 242);
    private static final Color WHITE         = Color.WHITE;
    private static final Color TEXT_DARK     = new Color(40, 35, 33);
    private static final Color TEXT_MUTED    = new Color(120, 110, 105);
    private static final Color FIELD_BG      = new Color(235, 233, 230);
    private static final Color BORDER_LIGHT  = new Color(210, 205, 200);

    private String studentName;

    public DashboardPage(String studentName) {
        this.studentName = studentName;

        setTitle("IIT-SHARE — Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 720);
        setMinimumSize(new Dimension(750, 550));
        setResizable(true);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 0));

        add(buildTopBar(),    BorderLayout.NORTH);
        add(buildMainPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    // ─────────────────────────────────────────────
    // TOP BAR
    // ─────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout(15, 0));
        bar.setBackground(WHITE);
        bar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, BORDER_LIGHT),
                new EmptyBorder(10, 20, 10, 20)
        ));

        // Logo
        JLabel logoLabel;
        try {
            URL imgURL = getClass().getResource("/logo.png.png");
            if (imgURL == null) throw new Exception();
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage().getScaledInstance(95, 52, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(img));
        } catch (Exception e) {
            logoLabel = new JLabel("IIT-SHARE");
            logoLabel.setFont(new Font("Serif", Font.BOLD, 20));
            logoLabel.setForeground(PRIMARY);
        }

        // Search bar
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        searchField.setBackground(FIELD_BG);
        searchField.setForeground(TEXT_DARK);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_LIGHT, 1, true),
                new EmptyBorder(6, 12, 6, 12)
        ));
        searchField.putClientProperty("JTextField.placeholderText", "Search for items...");

        JPanel searchWrap = new JPanel(new BorderLayout());
        searchWrap.setBackground(WHITE);
        searchWrap.setBorder(new EmptyBorder(4, 30, 4, 0));
        searchWrap.add(searchField, BorderLayout.CENTER);

        bar.add(logoLabel,  BorderLayout.WEST);
        bar.add(searchWrap, BorderLayout.CENTER);
        return bar;
    }

    // ─────────────────────────────────────────────
    // MAIN PANEL
    // ─────────────────────────────────────────────
    private JPanel buildMainPanel() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(BG);
        main.setBorder(new EmptyBorder(18, 22, 18, 22));

        main.add(buildNameRow());
        main.add(Box.createVerticalStrut(12));
        main.add(buildSearchRow());
        main.add(Box.createVerticalStrut(18));
        main.add(buildSectionLabel("AVAILABLE ITEMS"));
        main.add(Box.createVerticalStrut(12));
        main.add(buildItemsPanel());

        return main;
    }

    // ─────────────────────────────────────────────
    // NAME ROW
    // ─────────────────────────────────────────────
    private JPanel buildNameRow() {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setBackground(BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        // Name pill
        JPanel namePill = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        namePill.setBackground(WHITE);
        namePill.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_LIGHT, 1, true),
                new EmptyBorder(6, 8, 6, 8)
        ));
        JLabel nameLabel = new JLabel("👤  " + studentName);
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        nameLabel.setForeground(TEXT_DARK);
        namePill.add(nameLabel);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setBackground(BG);
        btnPanel.add(createRoundedButton("📋  Post Item", PRIMARY, WHITE));
        btnPanel.add(createRoundedButton("🕐  History",   PRIMARY, WHITE));

        row.add(namePill, BorderLayout.CENTER);
        row.add(btnPanel, BorderLayout.EAST);
        return row;
    }

    // ─────────────────────────────────────────────
    // SEARCH ROW
    // ─────────────────────────────────────────────
    private JPanel buildSearchRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        row.setBackground(WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_LIGHT, 1, true),
                new EmptyBorder(8, 14, 8, 14)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));

        JLabel lbl = new JLabel("Search items:");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(TEXT_DARK);

        JTextField itemField = styledField("e.g. Calculator", 130);

        JComboBox<String> collegeBox = styledCombo(new String[]{
                "All Colleges", "Computer Science", "Engineering", "Arts & Sciences"
        });

        JComboBox<String> programBox = styledCombo(new String[]{
                "All Programs", "Information Systems", "Computer Science", "Information Technology"
        });

        JButton searchBtn = createRoundedButton("Search", PRIMARY, WHITE);
        searchBtn.setPreferredSize(new Dimension(88, 32));

        JButton clearBtn = new JButton("Clear filters");
        clearBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        clearBtn.setForeground(ACCENT);
        clearBtn.setBackground(null);
        clearBtn.setBorderPainted(false);
        clearBtn.setContentAreaFilled(false);
        clearBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { clearBtn.setForeground(PRIMARY); }
            public void mouseExited(MouseEvent e)  { clearBtn.setForeground(ACCENT);  }
        });

        row.add(lbl);
        row.add(itemField);
        row.add(collegeBox);
        row.add(programBox);
        row.add(searchBtn);
        row.add(clearBtn);
        return row;
    }

    // ─────────────────────────────────────────────
    // SECTION LABEL
    // ─────────────────────────────────────────────
    private JLabel buildSectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(TEXT_MUTED);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    // ─────────────────────────────────────────────
    // ITEMS PANEL
    // ─────────────────────────────────────────────
    private JScrollPane buildItemsPanel() {
        JPanel grid = new JPanel(new GridLayout(1, 3, 16, 16));
        grid.setBackground(BG);

        String[][] items = {
                {"Lab Gown",   "Student A", "Available", "#CSM #BIO #lab"},
                {"Calculator", "Student B", "Available", "#COE #Calc #Math"},
                {"Tripod",     "Student C", "Available", "#CED #Film #Event"}
        };

        for (String[] item : items) {
            grid.add(createItemCard(item[0], item[1], item[2], item[3]));
        }

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        return scroll;
    }

    // ─────────────────────────────────────────────
    // ITEM CARD
    // ─────────────────────────────────────────────
    private JPanel createItemCard(String name, String owner, String status, String tags) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_LIGHT, 1, true),
                new EmptyBorder(12, 14, 14, 14)
        ));

        // Image placeholder
        JPanel imgBox = new JPanel(new BorderLayout());
        imgBox.setBackground(FIELD_BG);
        imgBox.setPreferredSize(new Dimension(0, 150));
        imgBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        imgBox.setBorder(new LineBorder(BORDER_LIGHT, 1));

        JLabel imgLbl = new JLabel("No Image", SwingConstants.CENTER);
        imgLbl.setFont(new Font("SansSerif", Font.ITALIC, 12));
        imgLbl.setForeground(TEXT_MUTED);
        imgBox.add(imgLbl, BorderLayout.CENTER);

        card.add(imgBox);
        card.add(Box.createVerticalStrut(12));

        // Item name
        JLabel nameLbl = new JLabel(name);
        nameLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        nameLbl.setForeground(TEXT_DARK);
        nameLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(nameLbl);
        card.add(Box.createVerticalStrut(4));

        // Owner
        JLabel ownerLbl = new JLabel("Owner: " + owner);
        ownerLbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        ownerLbl.setForeground(TEXT_MUTED);
        ownerLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(ownerLbl);
        card.add(Box.createVerticalStrut(2));

        // Status with colored dot
        JPanel statusRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        statusRow.setBackground(WHITE);
        statusRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel dot = new JLabel("●");
        dot.setFont(new Font("SansSerif", Font.PLAIN, 10));
        dot.setForeground(new Color(60, 160, 80));
        JLabel statusLbl = new JLabel(status);
        statusLbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLbl.setForeground(TEXT_MUTED);
        statusRow.add(dot);
        statusRow.add(statusLbl);
        card.add(statusRow);
        card.add(Box.createVerticalStrut(5));

        // Tags
        JLabel tagsLbl = new JLabel(tags);
        tagsLbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        tagsLbl.setForeground(ACCENT);
        tagsLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(tagsLbl);
        card.add(Box.createVerticalStrut(12));

        // Request button
        JButton reqBtn = createRoundedButton("Request", PRIMARY, WHITE);
        reqBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        reqBtn.setPreferredSize(new Dimension(100, 36));
        reqBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(reqBtn);

        return card;
    }

    // ─────────────────────────────────────────────
    // HELPERS
    // ─────────────────────────────────────────────
    private JTextField styledField(String placeholder, int width) {
        JTextField f = new JTextField();
        f.setPreferredSize(new Dimension(width, 32));
        f.setBackground(FIELD_BG);
        f.setForeground(TEXT_DARK);
        f.setFont(new Font("SansSerif", Font.PLAIN, 12));
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_LIGHT, 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));
        return f;
    }

    private JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> box = new JComboBox<>(items);
        box.setBackground(FIELD_BG);
        box.setForeground(TEXT_DARK);
        box.setFont(new Font("SansSerif", Font.PLAIN, 12));
        box.setPreferredSize(new Dimension(175, 32));
        box.setBorder(new LineBorder(BORDER_LIGHT, 1, true));
        return box;
    }

    private JButton createRoundedButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? bg.darker()
                        : getModel().isRollover() ? PRIMARY_HOVER : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(fg);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(fg);
        btn.setPreferredSize(new Dimension(130, 36));
        btn.setMaximumSize(new Dimension(150, 40));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.repaint(); }
            public void mouseExited(MouseEvent e)  { btn.repaint(); }
        });
        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }
        SwingUtilities.invokeLater(() -> new DashboardPage("Student ABC"));
    }
}