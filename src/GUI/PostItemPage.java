package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class PostItemPage extends JFrame {

    private static final Color PRIMARY       = new Color(50, 41, 38);
    private static final Color PRIMARY_HOVER = new Color(75, 62, 57);
    private static final Color BG            = new Color(245, 244, 242);
    private static final Color WHITE         = Color.WHITE;
    private static final Color TEXT_DARK     = new Color(40, 35, 33);
    private static final Color TEXT_MUTED    = new Color(120, 110, 105);
    private static final Color FIELD_BG      = new Color(235, 233, 230);
    private static final Color BORDER_LIGHT  = new Color(210, 205, 200);

    private String studentName;
    private JTextField nameField, detailsField, pricingField, hashtagsField;
    private JLabel imagePreviewLabel;
    private java.io.File selectedImageFile = null;

    public PostItemPage(String studentName) {
        this.studentName = studentName;

        setTitle("IIT-SHARE — Post Item");
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
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG);
        wrapper.setBorder(new EmptyBorder(24, 30, 24, 30));

        // ── Info bar (top instruction + POST button) ──
        JPanel infoBar = new JPanel(new BorderLayout(12, 0));
        infoBar.setBackground(WHITE);
        infoBar.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_LIGHT, 1, true),
                new EmptyBorder(10, 16, 10, 16)
        ));
        infoBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));

        JLabel infoLbl = new JLabel("To add an item, you must provide the following:");
        infoLbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        infoLbl.setForeground(TEXT_MUTED);

        JButton postBtn = createRoundedButton("POST");
        postBtn.setPreferredSize(new Dimension(100, 36));
        postBtn.addActionListener(e -> handlePost());

        infoBar.add(infoLbl, BorderLayout.CENTER);
        infoBar.add(postBtn, BorderLayout.EAST);

        // ── Left side: POST ITEM label ──
        JLabel sideLabel = new JLabel("<html>POST<br>ITEM</html>");
        sideLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        sideLabel.setForeground(new Color(230, 228, 225));
        sideLabel.setVerticalAlignment(SwingConstants.TOP);
        sideLabel.setBorder(new EmptyBorder(30, 0, 0, 20));

        // ── Center form ──
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(BG);

        // Image upload area
        JPanel imageSection = new JPanel();
        imageSection.setLayout(new BoxLayout(imageSection, BoxLayout.Y_AXIS));
        imageSection.setBackground(BG);
        imageSection.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel addImageLbl = new JLabel("Add image:");
        addImageLbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        addImageLbl.setForeground(TEXT_DARK);
        addImageLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        imagePreviewLabel = new JLabel("＋", SwingConstants.CENTER);
        imagePreviewLabel.setFont(new Font("SansSerif", Font.PLAIN, 28));
        imagePreviewLabel.setForeground(TEXT_MUTED);
        imagePreviewLabel.setBackground(FIELD_BG);
        imagePreviewLabel.setOpaque(true);
        imagePreviewLabel.setBorder(new LineBorder(BORDER_LIGHT, 1));
        imagePreviewLabel.setPreferredSize(new Dimension(130, 130));
        imagePreviewLabel.setMaximumSize(new Dimension(130, 130));
        imagePreviewLabel.setMinimumSize(new Dimension(130, 130));
        imagePreviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagePreviewLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        imagePreviewLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { chooseImage(); }
            public void mouseEntered(MouseEvent e) {
                imagePreviewLabel.setBorder(new LineBorder(PRIMARY, 2));
            }
            public void mouseExited(MouseEvent e) {
                imagePreviewLabel.setBorder(new LineBorder(BORDER_LIGHT, 1));
            }
        });

        imageSection.add(addImageLbl);
        imageSection.add(Box.createVerticalStrut(8));
        imageSection.add(imagePreviewLabel);

        // Fields
        nameField     = styledField();
        detailsField  = styledField();
        pricingField  = styledField();
        hashtagsField = styledField();

        formPanel.add(imageSection);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(buildFieldRow("Name:",     nameField));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buildFieldRow("Details:",  detailsField));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buildFieldRow("Pricing:",  pricingField));
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(buildFieldRow("Hashtags:", hashtagsField));

        // ── Layout: side label | info bar + form ──
        JPanel centerPanel = new JPanel(new BorderLayout(0, 14));
        centerPanel.setBackground(BG);
        centerPanel.add(infoBar,   BorderLayout.NORTH);
        centerPanel.add(formPanel, BorderLayout.CENTER);

        wrapper.add(sideLabel,   BorderLayout.WEST);
        wrapper.add(centerPanel, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel buildFieldRow(String labelText, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(TEXT_DARK);
        lbl.setPreferredSize(new Dimension(80, 38));
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);

        row.add(lbl,   BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    // ─────────────────────────────────────────────
    // ACTIONS
    // ─────────────────────────────────────────────
    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Images", "jpg", "jpeg", "png", "gif", "bmp"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = chooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(selectedImageFile.getAbsolutePath());
            Image scaled = icon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
            imagePreviewLabel.setIcon(new ImageIcon(scaled));
            imagePreviewLabel.setText("");
        }
    }

    private void handlePost() {
        String name     = nameField.getText().trim();
        String details  = detailsField.getText().trim();
        String pricing  = pricingField.getText().trim();
        String hashtags = hashtagsField.getText().trim();

        if (name.isEmpty() || details.isEmpty() || pricing.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in Name, Details, and Pricing.",
                    "Missing Fields", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Item \"" + name + "\" posted successfully!",
                "Posted!", JOptionPane.INFORMATION_MESSAGE);

        // Clear form
        nameField.setText("");
        detailsField.setText("");
        pricingField.setText("");
        hashtagsField.setText("");
        imagePreviewLabel.setIcon(null);
        imagePreviewLabel.setText("＋");
        selectedImageFile = null;
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

    private JButton createRoundedButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? PRIMARY.darker()
                        : getModel().isRollover() ? PRIMARY_HOVER : PRIMARY);
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