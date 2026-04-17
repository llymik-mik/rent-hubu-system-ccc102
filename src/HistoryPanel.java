package gui;

import main.DataStore;
import main.Item;
import main.Request;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoryPanel extends JPanel {

    private final AppFrame frame;

    public HistoryPanel(AppFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Theme.BG);
        buildUI();
    }

    private void buildUI() {
        // Top bar
        JPanel topBar = new JPanel(new BorderLayout(10, 0));
        topBar.setBackground(Theme.WHITE);
        topBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,0,1,0,Theme.LIGHT_GRAY),
                new EmptyBorder(6,12,6,12)));
        LogoHeader logo = new LogoHeader() {
            @Override public Dimension getPreferredSize() { return new Dimension(220,56); }
        };
        logo.setBorder(null);
        topBar.add(logo, BorderLayout.WEST);
        JTextField dummy = Theme.styledField(); dummy.setEnabled(false);
        topBar.add(dummy, BorderLayout.CENTER);

        JButton backBtn = Theme.secondaryButton("← Dashboard");
        backBtn.setPreferredSize(new Dimension(120,30));
        backBtn.addActionListener(e -> frame.navigate(AppFrame.DASHBOARD));
        topBar.add(backBtn, BorderLayout.EAST);
        add(topBar, BorderLayout.NORTH);

        // Tabbed pane
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(Theme.FONT_LABEL);
        tabs.setBackground(Theme.BG);

        tabs.addTab("My Requests",       buildRequestsTab());
        tabs.addTab("My Posted Items",   buildMyItemsTab());
        tabs.addTab("Incoming Requests", buildIncomingTab());

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Theme.BG);
        content.setBorder(new EmptyBorder(16,16,16,16));
        content.add(tabs, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    private JScrollPane buildRequestsTab() {
        String[] cols = {"Item", "Owner", "Borrow Date", "Return Date", "Contact", "Message", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        List<Request> reqs = DataStore.getInstance().getMyRequests();
        for (Request r : reqs) {
            model.addRow(new Object[]{
                    r.getItem().getName(), r.getItem().getOwnerName(),
                    r.getBorrowDate(), r.getReturnDate(),
                    r.getContactNumber(), r.getMessage(), r.getStatus()
            });
        }
        return scrollTable(model, reqs.isEmpty() ? "You have not made any requests yet." : null);
    }

    private JScrollPane buildMyItemsTab() {
        String[] cols = {"Name", "Details", "Pricing", "Hashtags", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        List<Item> items = DataStore.getInstance().getMyItems();
        for (Item it : items) {
            model.addRow(new Object[]{
                    it.getName(), it.getDetails(), it.getPricing(),
                    it.getHashtags(), it.getStatus()
            });
        }
        return scrollTable(model, items.isEmpty() ? "You have not posted any items yet." : null);
    }

    private JScrollPane buildIncomingTab() {
        String[] cols = {"Item", "Requester", "Borrow Date", "Return Date", "Contact", "Message", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        List<Request> reqs = DataStore.getInstance().getIncomingRequests();
        for (Request r : reqs) {
            model.addRow(new Object[]{
                    r.getItem().getName(), r.getRequester().getFullName(),
                    r.getBorrowDate(), r.getReturnDate(),
                    r.getContactNumber(), r.getMessage(), r.getStatus()
            });
        }
        return scrollTable(model, reqs.isEmpty() ? "No one has requested your items yet." : null);
    }

    private JScrollPane scrollTable(DefaultTableModel model, String emptyMsg) {
        JTable table = new JTable(model);
        table.setFont(Theme.FONT_BODY);
        table.setRowHeight(26);
        table.getTableHeader().setFont(Theme.FONT_LABEL);
        table.getTableHeader().setBackground(new Color(220,220,220));
        table.setGridColor(new Color(210,210,210));
        table.setSelectionBackground(new Color(200, 30, 30, 60));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.LIGHT_GRAY));
        if (emptyMsg != null) {
            JLabel lbl = new JLabel(emptyMsg, SwingConstants.CENTER);
            lbl.setFont(Theme.FONT_BODY);
            lbl.setForeground(Theme.TEXT_MED);
            scroll.setViewportView(lbl);
        }
        return scroll;
    }
}