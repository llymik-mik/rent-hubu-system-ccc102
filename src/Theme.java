package gui;

import java.awt.*;

/**
 * Central style constants — change once, applied everywhere.
 */
public class Theme {
    // Brand colours (from IIT-SHARE logo)
    public static final Color MAROON      = new Color(130, 0, 0);
    public static final Color DARK_MAROON = new Color(90, 0, 0);
    public static final Color ORANGE      = new Color(220, 100, 20);
    public static final Color BG          = new Color(245, 245, 245);
    public static final Color WHITE       = Color.WHITE;
    public static final Color LIGHT_GRAY  = new Color(220, 220, 220);
    public static final Color TEXT_DARK   = new Color(30, 30, 30);
    public static final Color TEXT_MED    = new Color(90, 90, 90);
    public static final Color CARD_BG     = Color.WHITE;
    public static final Color CARD_BORDER = new Color(200, 200, 200);

    // Fonts
    public static final Font FONT_TITLE  = new Font("SansSerif", Font.BOLD, 22);
    public static final Font FONT_LABEL  = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_BODY   = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_SMALL  = new Font("SansSerif", Font.PLAIN, 11);
    public static final Font FONT_BTN    = new Font("SansSerif", Font.BOLD, 13);

    // Button factory
    public static javax.swing.JButton primaryButton(String text) {
        javax.swing.JButton btn = new javax.swing.JButton(text);
        btn.setBackground(DARK_MAROON);
        btn.setForeground(WHITE);
        btn.setFont(FONT_BTN);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        return btn;
    }

    public static javax.swing.JButton secondaryButton(String text) {
        javax.swing.JButton btn = new javax.swing.JButton(text);
        btn.setBackground(LIGHT_GRAY);
        btn.setForeground(TEXT_DARK);
        btn.setFont(FONT_BTN);
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(180,180,180)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        return btn;
    }

    public static javax.swing.JTextField styledField() {
        javax.swing.JTextField f = new javax.swing.JTextField();
        f.setFont(FONT_BODY);
        f.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(LIGHT_GRAY),
                javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        f.setBackground(new Color(235, 235, 235));
        return f;
    }

    public static javax.swing.JPasswordField styledPassword() {
        javax.swing.JPasswordField f = new javax.swing.JPasswordField();
        f.setFont(FONT_BODY);
        f.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(LIGHT_GRAY),
                javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        f.setBackground(new Color(235, 235, 235));
        return f;
    }
}