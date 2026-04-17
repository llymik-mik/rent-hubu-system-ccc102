package gui;

import javax.swing.*;
import java.awt.*;

/**
 * AppFrame — the single JFrame that hosts all screens via CardLayout.
 * Navigation happens by calling AppFrame.show("screenName").
 */
public class AppFrame extends JFrame {

    public static final String LOGIN     = "LOGIN";
    public static final String SIGNUP    = "SIGNUP";
    public static final String DASHBOARD = "DASHBOARD";
    public static final String POST_ITEM = "POST_ITEM";
    public static final String HISTORY   = "HISTORY";

    private static AppFrame instance;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Panels (lazily created or recreated on navigation)
    private LoginPanel loginPanel;
    private SignupPanel signupPanel;

    public static AppFrame getInstance() {
        if (instance == null) instance = new AppFrame();
        return instance;
    }

    private AppFrame() {
        super("IIT-SHARE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 620);
        setMinimumSize(new Dimension(760, 540));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Theme.BG);

        loginPanel  = new LoginPanel(this);
        signupPanel = new SignupPanel(this);

        cardPanel.add(loginPanel,  LOGIN);
        cardPanel.add(signupPanel, SIGNUP);

        add(cardPanel);
        cardLayout.show(cardPanel, LOGIN);
    }

    /**
     * Navigate to a named screen. Dashboard, PostItem, History are
     * re-instantiated each visit so they always show fresh data.
     */
    public void navigate(String screen) {
        switch (screen) {
            case DASHBOARD:
                DashboardPanel dash = new DashboardPanel(this);
                cardPanel.add(dash, DASHBOARD);
                cardLayout.show(cardPanel, DASHBOARD);
                break;
            case POST_ITEM:
                PostItemPanel post = new PostItemPanel(this);
                cardPanel.add(post, POST_ITEM);
                cardLayout.show(cardPanel, POST_ITEM);
                break;
            case HISTORY:
                HistoryPanel hist = new HistoryPanel(this);
                cardPanel.add(hist, HISTORY);
                cardLayout.show(cardPanel, HISTORY);
                break;
            case LOGIN:
                cardLayout.show(cardPanel, LOGIN);
                break;
            case SIGNUP:
                cardLayout.show(cardPanel, SIGNUP);
                break;
        }
    }

    /**
     * Show the RequestItem dialog over the current screen.
     */
    public void showRequestDialog(main.Item item) {
        new RequestItemDialog(this, item);
    }
}   