package gui;

import main.DataStore;
import main.Item;
import main.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class DashboardPanel extends JPanel {

    private final AppFrame frame;
    private JTextField searchField;
    private JComboBox<String> collegeCombo, courseCombo;
    private JPanel itemsGrid;

    private static final String[] COLLEGES = {
            "All Colleges", "College of Computer Science", "College of Engineering",
            "College of Business", "College of Education", "College of Arts"
    };
    private static final String[] COURSES = {
            "All Courses", "Information Systems", "Computer Science",
            "Information Technology", "Engineering", "Business Admin"
    };

    public DashboardPanel(AppFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        buildUI();
        loadItems(DataStore.getInstance().getAvailableItems());
    }

    private void buildUI() {
        // ── Top bar ──────────────────────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout(10, 0));
        topBar.setBackground(Theme.WHITE);
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.LIGHT_GRAY),
                new EmptyBorder(6, 12, 6, 12)));

        // Logo left
        LogoHeader logo = new LogoHeader() {
            @Override public Dimension getPreferredSize() { return new Dimension(220, 56); }
        };
        logo.setBorder(null);
        topBar.add(logo, BorderLayout.WEST);

        // Search bar center
        JTextField topSearch = Theme.styledField();
        topSearch.setFont(Theme.FONT_BODY);
        topSearch.setToolTipText("Search anything...");
        topBar.add(topSearch, BorderLayout.CENTER);

        // Action buttons right
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        btnPanel.setBackground(Theme.WHITE);
        JButton postBtn = Theme.primaryButton("Post Item");
        JButton histBtn = Theme.primaryButton("History");
        postBtn.addActionListener(e -> frame.navigate(AppFrame.POST_ITEM));
        histBtn.addActionListener(e -> frame.navigate(AppFrame.HISTORY));

        // Logout
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(Theme.FONT_SMALL);
        logoutBtn.setForeground(Theme.MAROON);
        logoutBtn.setBackground(null);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            DataStore.getInstance().logout();
            frame.navigate(AppFrame.LOGIN);
        });

        btnPanel.add(postBtn); btnPanel.add(histBtn); btnPanel.add(logoutBtn);
        topBar.add(btnPanel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // ── Centre area ──────────────────────────────────────────────────────
        JPanel centre = new JPanel(new BorderLayout(0, 10));
        centre.setBackground(Theme.BG);
        centre.setBorder(new EmptyBorder(12, 14, 12, 14));

        // Name strip + search filters
        JPanel nameRow = new JPanel(new BorderLayout(10, 0));
        nameRow.setBackground(Theme.BG);

        User user = DataStore.getInstance().getCurrentUser();
        JLabel nameLabel = new JLabel("Name:  " + (user != null ? user.getFullName() : ""));
        nameLabel.setFont(Theme.FONT_LABEL);
        nameLabel.setOpaque(true);
        nameLabel.setBackground(new Color(230, 230, 230));
        nameLabel.setBorder(new EmptyBorder(6, 12, 6, 12));
        nameRow.add(nameLabel, BorderLayout.CENTER);
        centre.add(nameRow, BorderLayout.NORTH);

        // Filter bar
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        filterBar.setBackground(Theme.BG);

        JLabel searchLbl = new JLabel("Search items:");
        searchLbl.setFont(Theme.FONT_LABEL);
        searchField = Theme.styledField();
        searchField.setPreferredSize(new Dimension(140, 28));

        collegeCombo = new JComboBox<>(COLLEGES);
        courseCombo  = new JComboBox<>(COURSES);
        styleCombo(collegeCombo); styleCombo(courseCombo);

        JButton searchBtn = Theme.primaryButton("Search");
        searchBtn.setPreferredSize(new Dimension(80, 28));
        searchBtn.addActionListener(e -> doSearch());

        JButton clearBtn = new JButton("Clear filters");
        clearBtn.setFont(Theme.FONT_SMALL);
        clearBtn.setBackground(null); clearBtn.setBorderPainted(false);
        clearBtn.setContentAreaFilled(false);
        clearBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearBtn.addActionListener(e -> {
            searchField.setText(""); collegeCombo.setSelectedIndex(0); courseCombo.setSelectedIndex(0);
            loadItems(DataStore.getInstance().getAvailableItems());
        });

        filterBar.add(searchLbl); filterBar.add(searchField);
        filterBar.add(collegeCombo); filterBar.add(courseCombo);
        filterBar.add(searchBtn); filterBar.add(clearBtn);
        centre.add(filterBar, BorderLayout.CENTER);

        // Items grid scroll
        JPanel gridWrapper = new JPanel(new BorderLayout());
        gridWrapper.setBackground(Theme.BG);

        JLabel availLbl = new JLabel("AVAILABLE ITEMS:");
        availLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        availLbl.setBorder(new EmptyBorder(6, 0, 6, 0));
        gridWrapper.add(availLbl, BorderLayout.NORTH);

        itemsGrid = new JPanel(new WrapLayout(FlowLayout.LEFT, 14, 14));
        itemsGrid.setBackground(Theme.BG);

        JScrollPane scroll = new JScrollPane(itemsGrid);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        gridWrapper.add(scroll, BorderLayout.CENTER);

        centre.add(gridWrapper, BorderLayout.SOUTH);

        // make gridWrapper expand
        centre.setLayout(new BorderLayout(0, 8));
        centre.add(nameRow, BorderLayout.NORTH);
        JPanel mid = new JPanel(new BorderLayout());
        mid.setBackground(Theme.BG);
        mid.add(filterBar, BorderLayout.NORTH);
        mid.add(gridWrapper, BorderLayout.CENTER);
        centre.add(mid, BorderLayout.CENTER);

        add(centre, BorderLayout.CENTER);
    }

    private void doSearch() {
        String kw  = searchField.getText().trim();
        String col = (String) collegeCombo.getSelectedItem();
        String crs = (String) courseCombo.getSelectedItem();
        List<Item> results = DataStore.getInstance().searchItems(kw, col, crs);
        loadItems(results);
    }

    private void loadItems(List<Item> items) {
        itemsGrid.removeAll();
        if (items.isEmpty()) {
            JLabel empty = new JLabel("No items found.");
            empty.setFont(Theme.FONT_BODY);
            empty.setForeground(Theme.TEXT_MED);
            itemsGrid.add(empty);
        } else {
            for (Item item : items) {
                itemsGrid.add(new ItemCard(item, frame));
            }
        }
        itemsGrid.revalidate();
        itemsGrid.repaint();
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setFont(Theme.FONT_SMALL);
        combo.setBackground(new Color(230,230,230));
        combo.setPreferredSize(new Dimension(180, 28));
    }

    // ── WrapLayout helper ────────────────────────────────────────────────────
    static class WrapLayout extends FlowLayout {
        WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }
        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }
        @Override
        public Dimension minimumLayoutSize(Container target) {
            return layoutSize(target, false);
        }
        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int targetWidth = target.getSize().width;
                if (targetWidth == 0) targetWidth = Integer.MAX_VALUE;
                int hgap = getHgap(), vgap = getVgap();
                Insets insets = target.getInsets();
                int maxWidth = targetWidth - insets.left - insets.right - hgap * 2;
                int height = vgap, rowHeight = 0, rowWidth = hgap;
                for (int i = 0; i < target.getComponentCount(); i++) {
                    Component c = target.getComponent(i);
                    if (!c.isVisible()) continue;
                    Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();
                    if (rowWidth + d.width > maxWidth && rowWidth > hgap) {
                        height += rowHeight + vgap;
                        rowWidth = hgap; rowHeight = 0;
                    }
                    rowWidth += d.width + hgap;
                    rowHeight = Math.max(rowHeight, d.height);
                }
                height += rowHeight + vgap + insets.top + insets.bottom;
                return new Dimension(targetWidth, height);
            }
        }
    }
}