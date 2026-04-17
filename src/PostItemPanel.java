package gui;

import main.DataStore;
import main.Item;
import main.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class PostItemPanel extends JPanel {

    private final AppFrame frame;
    private JTextField nameField, detailsField, pricingField, hashtagsField;
    private BufferedImage selectedImage;
    private JPanel imagePreview;

    public PostItemPanel(AppFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        buildUI();
    }

    private void buildUI() {
        // ── Top bar ──────────────────────────────────────────────────────────
        JPanel topBar = buildTopBar();
        add(topBar, BorderLayout.NORTH);

        // ── Form ─────────────────────────────────────────────────────────────
        JPanel formWrapper = new JPanel(new GridBagLayout());
        formWrapper.setBackground(Theme.BG);
        formWrapper.setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel form = new JPanel();
        form.setBackground(Theme.WHITE);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.LIGHT_GRAY),
                new EmptyBorder(16, 20, 16, 20)));
        form.setPreferredSize(new Dimension(520, 480));

        // Instruction + POST button row
        JPanel instrRow = new JPanel(new BorderLayout(10, 0));
        instrRow.setBackground(Theme.WHITE);
        instrRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        JLabel instr = new JLabel("To add an item, you must provide the following:");
        instr.setFont(Theme.FONT_BODY);
        instr.setOpaque(true);
        instr.setBackground(new Color(230,230,230));
        instr.setBorder(new EmptyBorder(4,10,4,10));
        JButton postBtn = Theme.primaryButton("POST");
        postBtn.setPreferredSize(new Dimension(80, 30));
        postBtn.addActionListener(e -> doPost());
        instrRow.add(instr, BorderLayout.CENTER);
        instrRow.add(postBtn, BorderLayout.EAST);
        form.add(instrRow);
        form.add(Box.createVerticalStrut(16));

        // Image area
        JLabel addImgLbl = new JLabel("Add image:");
        addImgLbl.setFont(Theme.FONT_LABEL);
        addImgLbl.setAlignmentX(CENTER_ALIGNMENT);
        form.add(addImgLbl);
        form.add(Box.createVerticalStrut(6));

        imagePreview = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (selectedImage != null) {
                    g.drawImage(selectedImage, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g.setColor(new Color(230,230,230));
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.setColor(Theme.TEXT_MED);
                    g.setFont(Theme.FONT_SMALL);
                    g.drawString("Click to browse", 20, getHeight() / 2);
                }
            }
        };
        imagePreview.setPreferredSize(new Dimension(110, 110));
        imagePreview.setMaximumSize(new Dimension(110, 110));
        imagePreview.setBorder(BorderFactory.createLineBorder(Theme.LIGHT_GRAY));
        imagePreview.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        imagePreview.setAlignmentX(CENTER_ALIGNMENT);
        imagePreview.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) { browseImage(); }
        });
        form.add(imagePreview);
        form.add(Box.createVerticalStrut(16));

        // Fields
        nameField     = Theme.styledField();
        detailsField  = Theme.styledField();
        pricingField  = Theme.styledField();
        hashtagsField = Theme.styledField();

        form.add(fieldRow("Name:",     nameField));
        form.add(Box.createVerticalStrut(10));
        form.add(fieldRow("Details:",  detailsField));
        form.add(Box.createVerticalStrut(10));
        form.add(fieldRow("Pricing:",  pricingField));
        form.add(Box.createVerticalStrut(10));
        form.add(fieldRow("Hashtags:", hashtagsField));
        form.add(Box.createVerticalStrut(10));

        // Back button
        JButton backBtn = new JButton("← Back to Dashboard");
        backBtn.setFont(Theme.FONT_SMALL);
        backBtn.setForeground(Theme.MAROON);
        backBtn.setBackground(null);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.setAlignmentX(LEFT_ALIGNMENT);
        backBtn.addActionListener(e -> frame.navigate(AppFrame.DASHBOARD));
        form.add(backBtn);

        formWrapper.add(form);
        add(formWrapper, BorderLayout.CENTER);
    }

    private void browseImage() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Images", "jpg", "jpeg", "png", "gif", "bmp"));
        if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            try {
                selectedImage = ImageIO.read(f);
                imagePreview.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Could not load image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void doPost() {
        String name     = nameField.getText().trim();
        String details  = detailsField.getText().trim();
        String pricing  = pricingField.getText().trim();
        String hashtags = hashtagsField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Item name is required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = DataStore.getInstance().getCurrentUser();
        Item item = new Item(name, details, pricing.isEmpty() ? "Free" : pricing,
                hashtags, user.getFullName(), user.getIdNumber());
        if (selectedImage != null) item.setImage(selectedImage);

        DataStore.getInstance().postItem(item);
        JOptionPane.showMessageDialog(frame, "Item posted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        frame.navigate(AppFrame.DASHBOARD);
    }

    private JPanel fieldRow(String labelText, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBackground(Theme.WHITE);
        row.setAlignmentX(LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(Theme.FONT_LABEL);
        lbl.setPreferredSize(new Dimension(76, 28));
        row.add(lbl, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private JPanel buildTopBar() {
        JPanel topBar = new JPanel(new BorderLayout(10, 0));
        topBar.setBackground(Theme.WHITE);
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.LIGHT_GRAY),
                new EmptyBorder(6, 12, 6, 12)));

        LogoHeader logo = new LogoHeader() {
            @Override public Dimension getPreferredSize() { return new Dimension(220, 56); }
        };
        logo.setBorder(null);
        topBar.add(logo, BorderLayout.WEST);

        JTextField dummy = Theme.styledField();
        dummy.setEnabled(false);
        topBar.add(dummy, BorderLayout.CENTER);
        return topBar;
    }
}