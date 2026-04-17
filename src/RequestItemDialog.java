package gui;

import main.DataStore;
import main.Item;
import main.Request;
import main.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RequestItemDialog extends JDialog {

    private final Item item;
    private JTextField borrowDateField, returnDateField, contactField, messageField;

    public RequestItemDialog(AppFrame owner, Item item) {
        super(owner, "Request Item", true);
        this.item = item;
        setSize(640, 460);
        setLocationRelativeTo(owner);
        setResizable(false);
        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.BG);

        // ── Top bar ──────────────────────────────────────────────────────────
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
        JTextField dummy = Theme.styledField(); dummy.setEnabled(false);
        topBar.add(dummy, BorderLayout.CENTER);
        root.add(topBar, BorderLayout.NORTH);

        // ── Centre: form + item card ──────────────────────────────────────────
        JPanel centre = new JPanel(new BorderLayout(16, 0));
        centre.setBackground(Theme.BG);
        centre.setBorder(new EmptyBorder(16, 20, 16, 20));

        // Left: form
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Theme.BG);

        JLabel title = new JLabel("Request Item");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setAlignmentX(LEFT_ALIGNMENT);
        form.add(title);
        form.add(Box.createVerticalStrut(10));

        JLabel instr = new JLabel("To request an item, you must provide the following:");
        instr.setFont(Theme.FONT_SMALL);
        instr.setOpaque(true);
        instr.setBackground(new Color(230,230,230));
        instr.setBorder(new EmptyBorder(4,8,4,8));
        instr.setAlignmentX(LEFT_ALIGNMENT);
        form.add(instr);
        form.add(Box.createVerticalStrut(12));

        borrowDateField  = Theme.styledField();
        returnDateField  = Theme.styledField();
        contactField     = Theme.styledField();
        messageField     = Theme.styledField();

        borrowDateField.setToolTipText("e.g. 2025-08-01");
        returnDateField.setToolTipText("e.g. 2025-08-07");

        form.add(fRow("Borrow Date:",        borrowDateField));
        form.add(Box.createVerticalStrut(8));
        form.add(fRow("Return Date:",        returnDateField));
        form.add(Box.createVerticalStrut(8));
        form.add(fRow("Contact Number:",     contactField));
        form.add(Box.createVerticalStrut(8));
        form.add(fRow("Message to the owner:", messageField));
        form.add(Box.createVerticalStrut(12));

        // Reminders
        JTextArea reminders = new JTextArea(
                "Reminders:\n• Renters should show their ID.\n• Return items on TIME.");
        reminders.setFont(Theme.FONT_SMALL);
        reminders.setEditable(false);
        reminders.setBackground(Theme.BG);
        reminders.setAlignmentX(LEFT_ALIGNMENT);
        form.add(reminders);
        form.add(Box.createVerticalStrut(12));

        // Buttons
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        btnRow.setBackground(Theme.BG);
        btnRow.setAlignmentX(LEFT_ALIGNMENT);

        JButton submitBtn = Theme.primaryButton("Submit Request");
        submitBtn.addActionListener(e -> doSubmit());

        JButton cancelBtn = Theme.primaryButton("Cancel");
        cancelBtn.setBackground(new Color(60,60,60));
        cancelBtn.addActionListener(e -> dispose());

        btnRow.add(submitBtn); btnRow.add(cancelBtn);
        form.add(btnRow);
        centre.add(form, BorderLayout.CENTER);

        // Right: item card preview
        JPanel cardPreview = buildItemPreview();
        centre.add(cardPreview, BorderLayout.EAST);

        root.add(centre, BorderLayout.CENTER);
        setContentPane(root);
    }

    private JPanel buildItemPreview() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Theme.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.CARD_BORDER),
                new EmptyBorder(10, 10, 10, 10)));
        card.setPreferredSize(new Dimension(180, 260));

        // Image
        JPanel imgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (item.getImage() != null) {
                    g.drawImage(item.getImage(), 0, 0, getWidth(), getHeight(), null);
                } else {
                    g.setColor(new Color(230,230,230));
                    g.fillRect(0,0,getWidth(),getHeight());
                    g.setColor(new Color(180,0,0,100));
                    g.setFont(new Font("SansSerif", Font.BOLD, 36));
                    String s = item.getName().substring(0,1).toUpperCase();
                    FontMetrics fm = g.getFontMetrics();
                    g.drawString(s, (getWidth()-fm.stringWidth(s))/2,
                            (getHeight()+fm.getAscent()-fm.getDescent())/2);
                }
            }
        };
        imgPanel.setPreferredSize(new Dimension(158, 130));
        imgPanel.setMaximumSize(new Dimension(158, 130));
        imgPanel.setBorder(BorderFactory.createLineBorder(new Color(200,100,0)));
        imgPanel.setAlignmentX(CENTER_ALIGNMENT);
        card.add(imgPanel);
        card.add(Box.createVerticalStrut(8));

        card.add(bold("Item: " + item.getName()));
        card.add(plain("Owner: " + item.getOwnerName()));
        card.add(plain("Status: " + item.getStatus()));
        card.add(small(item.getHashtags()));
        return card;
    }

    private void doSubmit() {
        String borrow  = borrowDateField.getText().trim();
        String ret     = returnDateField.getText().trim();
        String contact = contactField.getText().trim();
        String msg     = messageField.getText().trim();

        if (borrow.isEmpty() || ret.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in Borrow Date, Return Date and Contact Number.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = DataStore.getInstance().getCurrentUser();
        Request req = new Request(item, user, borrow, ret, contact, msg);
        DataStore.getInstance().submitRequest(req);

        JOptionPane.showMessageDialog(this,
                "Request submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    private JPanel fRow(String labelText, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(6, 0));
        row.setBackground(Theme.BG);
        row.setAlignmentX(LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(Theme.FONT_LABEL);
        lbl.setPreferredSize(new Dimension(160, 28));
        row.add(lbl, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private JLabel bold(String t)  { JLabel l=new JLabel(t); l.setFont(new Font("SansSerif",Font.BOLD,12)); return l; }
    private JLabel plain(String t) { JLabel l=new JLabel(t); l.setFont(Theme.FONT_SMALL); return l; }
    private JLabel small(String t) { JLabel l=new JLabel(t); l.setFont(new Font("SansSerif",Font.ITALIC,11)); l.setForeground(Theme.TEXT_MED); return l; }
}