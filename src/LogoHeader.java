package gui;

import javax.swing.*;
import java.awt.*;

/**
 * A branded header bar that can be placed at the top of any panel.
 * Shows the IIT-SHARE text logo (drawn with Java2D — no image file needed).
 */
public class LogoHeader extends JPanel {

    public LogoHeader() {
        setBackground(Theme.WHITE);
        setPreferredSize(new Dimension(0, 70));
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Theme.MAROON));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw book icon
        int bx = 14, by = 12, bw = 36, bh = 44;
        g2.setColor(Theme.MAROON);
        g2.fillRoundRect(bx, by, bw, bh, 6, 6);
        g2.setColor(Theme.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2.drawString("📖", bx + 2, by + bh - 8);

        // Draw share arrows overlay
        g2.setColor(new Color(255,255,255,200));
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        g2.drawString("⇄", bx + 10, by + 20);

        // Paw print
        g2.setColor(Theme.ORANGE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 30));
        g2.drawString("🐾", bx + bw + 2, by + bh - 4);

        // IIT-SHARE text
        g2.setColor(Theme.MAROON);
        g2.setFont(new Font("SansSerif", Font.BOLD, 22));
        g2.drawString("IIT-SHARE", bx + bw + 38, by + bh - 10);

        // Underline swoosh
        g2.setColor(Theme.ORANGE);
        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawArc(bx - 4, by + bh - 10, 220, 18, 200, -200);
    }
}