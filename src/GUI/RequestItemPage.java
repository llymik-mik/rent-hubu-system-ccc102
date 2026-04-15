package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class RequestItemPage extends JFrame {

    private static final Color PRIMARY       = new Color(50, 41, 38);
    private static final Color PRIMARY_HOVER = new Color(75, 62, 57);
    private static final Color ACCENT        = new Color(180, 55, 45);
    private static final Color BG            = new Color(245, 244, 242);
    private static final Color WHITE         = Color.WHITE;
    private static final Color TEXT_DARK     = new Color(40, 35, 33);
    private static final Color TEXT_MUTED    = new Color(120, 110, 105);
    private static final Color FIELD_BG      = new Color(235, 233, 230);
    private static final Color BORDER_LIGHT  = new Color(210, 205, 200);
    private static final Color CARD_BORDER   = new Color(180, 55, 45);

    private String studentName;
    private String itemName, itemOwner, itemStatus, itemTags;

    private JTextField borrowDateField, returnDateField, contactField, messageField;

    public RequestItemPage(String studentName, String itemName,
                           String itemOwner, String itemStatus, String itemTags) {
        this.studentName = studentName;
        this.itemName    = itemName;
        this.itemOwner   = itemOwner;
        this.itemStatus  = itemStatus;
        this.itemTags    = itemTags;

        setTitle("IIT-SHARE — Request Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 720);
        setMinimumSize(new Dimension(750, 550));
        setResizable(true);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(0, 0));

        add(buildTopBar(), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);

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

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        searchField.setBackground(FIELD_BG);
        searchField.setForeground(TEXT_DARK);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_LIGHT, 1, true),
                new EmptyBorder(6, 12, 6, 12)
        ));

        JPanel searchWrap = new JPanel(new BorderLayout());
        searchWrap.setBackground(WHITE);
        searchWrap.setBorder(new EmptyBorder(4, 30, 4, 0));
        searchWrap.add(searchField, BorderLayout.CENTER);

        bar.add(logoLabel, BorderLayout.WEST);
        bar.add(searchWrap, BorderLayout.CENTER);
        return bar;
    }

    // ─────────────────────────────────────────────
    // MAIN CONTENT
    // ─────────────────────────────────────────────
    private JPanel buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout(24, 0));
        wrapper.setBackground(BG);
        wrapper.setBorder(new EmptyBorder(24, 30, 24, 30));

        // ── LEFT SIDE ──
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(BG);

        // Page title
        JLabel titleLbl = new JLabel("Request Item");
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLbl.setForeground(TEXT_DARK);
        titleLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Info bar
        JPanel infoBar = new JPanel(new BorderLayout());
        infoBar.setBackground(WHITE);
        infoBar.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_LIGHT, 1, true),
                new EmptyBorder(10, 16, 10, 16)
        ));
        infoBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        JLabel infoLbl = new JLabel("To request an item, you must provide the following:");
        infoLbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        infoLbl.setForeground(TEXT_MUTED);
        infoBar.add(infoLbl, BorderLayout.CENTER);

        // Side watermark label
        JLabel sideLabel = new JLabel("<html>REQUEST<br>ITEM</html>");
        sideLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        sideLabel.setForeground(new Color(230, 228, 225));
        sideLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Form fields
        borrowDateField = styledField();
        returnDateField = styledField();
        contactField    = styledField();
        messageField    = styledField();

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(BG);
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(buildFieldRow("Borrow Date:",        borrowDateField));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buildFieldRow("Return Date:",        returnDateField));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buildFieldRow("Contact Number:",     contactField));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buildFieldRow("Message to the owner:", messageField));
        formPanel.add(Box.createVerticalStrut(20));

        // Reminders
        JPanel remindersPanel = new JPanel();
        remindersPanel.setLayout(new BoxLayout(remindersPanel, BoxLayout.Y_AXIS));
        remindersPanel.setBackground(BG);
        remindersPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel remLbl = new JLabel("Reminders:");
        remLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        remLbl.setForeground(TEXT_MUTED);
        remLbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel rem1 = new JLabel("  •  Renters should show their ID.");
        JLabel rem2 = new JLabel("  •  Return items on TIME.");
        for (JLabel r : new JLabel[]{rem1, rem2}) {
            r.setFont(new Font("SansSerif", Font.PLAIN, 12));
            r.setForeground(TEXT_MUTED);
            r.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        remindersPanel.add(remLbl);
        remindersPanel.add(Box.createVerticalStrut(4));
        remindersPanel.add(rem1);
        remindersPanel.add(rem2);
        remindersPanel.add(Box.createVerticalStrut(20));

        // Buttons
        JButton submitBtn = createRoundedButton("Submit Request", PRIMARY);
        JButton cancelBtn = createRoundedButton("Cancel", new Color(80, 75, 72));
        submitBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        cancelBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitBtn.setMaximumSize(new Dimension(180, 40));
        cancelBtn.setMaximumSize(new Dimension(180, 40));

        submitBtn.addActionListener(e -> handleSubmit());
        cancelBtn.addActionListener(e -> dispose());

        leftPanel.add(titleLbl);
        leftPanel.add(Box.createVerticalStrut(12));
        leftPanel.add(infoBar);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(sideLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(formPanel);
        leftPanel.add(remindersPanel);
        leftPanel.add(submitBtn);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(cancelBtn);

        // ── RIGHT SIDE: Item card ──
        JPanel itemCard = buildItemCard();

        wrapper.add(leftPanel, BorderLayout.CENTER);
        wrapper.add(itemCard,  BorderLayout.EAST);
        return wrapper;
    }

    private JPanel buildItemCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);
        card.setPreferredSize(new Dimension(200, 0));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(CARD_BORDER, 1, true),
                new EmptyBorder(12, 12, 14, 12)
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

        // Item info
        JLabel info = new JLabel("<html>"
                + "<b>Item:</b> "   + itemName   + "<br>"
                + "<b>Owner:</b> "  + itemOwner  + "<br>"
                + "<b>Status:</b> " + itemStatus + "<br>"
                + "<font color='#888888'>" + itemTags + "</font>"
                + "</html>");
        info.setFont(new Font("SansSerif", Font.PLAIN, 13));
        info.setForeground(TEXT_DARK);
        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(info);

        return card;
    }

    private JPanel buildFieldRow(String labelText, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(TEXT_DARK);
        lbl.setPreferredSize(new Dimension(170, 38));
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(lbl,   BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    // ─────────────────────────────────────────────
    // ACTIONS
    // ─────────────────────────────────────────────
    private void handleSubmit() {
        String borrow  = borrowDateField.getText().trim();
        String ret     = returnDateField.getText().trim();
        String contact = contactField.getText().trim();

        if (borrow.isEmpty() || ret.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in Borrow Date, Return Date, and Contact Number.",
                    "Missing Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Request for \"" + itemName + "\" submitted successfully!\n"
                        + "Borrow: " + borrow + "  →  Return: " + ret,
                "Request Submitted!", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    // ─────────────────────────────────────────────
    // HELPERS
    // ─────────────────────────────────────────────
    private JTextField styledField() {
        JTextField f = new JTextField();
        f.setBackground(FIELD_BG);
        f.setForeground(TEXT_DARK);
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_LIGHT, 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        return f;
    }

    private JButton createRoundedButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? bg.darker()
                        : getModel().isRollover() ? bg.brighter() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {}
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(160, 38));
        btn.setMaximumSize(new Dimension(180, 40));
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
}