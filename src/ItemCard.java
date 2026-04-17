package gui;

import main.Item;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A single item card shown in the dashboard grid.
 */
public class ItemCard extends JPanel {

    private final Item item;
    private final AppFrame frame;

    public ItemCard(Item item, AppFrame frame) {
        this.item  = item;
        this.frame = frame;
        setLayout(new BorderLayout(0, 6));
        setBackground(Theme.CARD_BG);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.CARD_BORDER, 1),
                new EmptyBorder(10, 10, 10, 10)));
        setPreferredSize(new Dimension(200, 270));
        setMaximumSize(new Dimension(220, 280));
        buildUI();
    }

    private void buildUI() {
        // Image area
        JPanel imgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                BufferedImage img = item.getImage();
                if (img != null) {
                    g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);
                } else {
                    // Draw a placeholder with item initial
                    g2.setColor(new Color(230, 230, 230));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(new Color(180, 0, 0, 120));
                    g2.setFont(new Font("SansSerif", Font.BOLD, 48));
                    String initial = item.getName().substring(0, 1).toUpperCase();
                    FontMetrics fm = g2.getFontMetrics();
                    int tx = (getWidth()  - fm.stringWidth(initial)) / 2;
                    int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                    g2.drawString(initial, tx, ty);
                }
            }
        };
        imgPanel.setBackground(new Color(235, 235, 235));
        imgPanel.setPreferredSize(new Dimension(180, 140));
        imgPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 100, 0), 1));
        add(imgPanel, BorderLayout.NORTH);

        // Info text
        JPanel info = new JPanel();
        info.setBackground(Theme.CARD_BG);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel nameL  = bold("Item: " + item.getName());
        JLabel ownerL = plain("Owner: " + item.getOwnerName());
        JLabel statL  = plain("Status: " + item.getStatus());
        JLabel hashL  = small(item.getHashtags());
        hashL.setForeground(Theme.TEXT_MED);

        info.add(nameL);
        info.add(ownerL);
        info.add(statL);
        info.add(Box.createVerticalStrut(2));
        info.add(hashL);
        add(info, BorderLayout.CENTER);

        // Request button
        JButton reqBtn = Theme.secondaryButton("Request");
        reqBtn.setFont(Theme.FONT_SMALL);
        reqBtn.setPreferredSize(new Dimension(100, 28));
        reqBtn.addActionListener(e -> frame.showRequestDialog(item));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 2));
        btnRow.setBackground(Theme.CARD_BG);
        btnRow.add(reqBtn);
        add(btnRow, BorderLayout.SOUTH);
    }

    private JLabel bold(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        return l;
    }

    private JLabel plain(String text) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.FONT_SMALL);
        return l;
    }

    private JLabel small(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.ITALIC, 11));
        return l;
    }
}