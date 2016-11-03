package nl.infosys.hartigehap.supplySystem.presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import nl.infosys.hartigehap.supplySystem.businesslogic.IngredientManager;
import nl.infosys.hartigehap.supplySystem.businesslogic.PurchaseManager;
import nl.infosys.hartigehap.userSystem.businesslogic.UserManager;
import nl.infosys.hartigehap.userSystem.businesslogic.UserManagerImpl;

/**
 *
 * @author Gregor
 */
public class SupplyGui {

    private PurchaseOverviewPanel orderOverviewGUI, quotationOverviewGUI;
    private StockOverviewPanel stockGUI;
    private JTabbedPane tabbedPane;
    private final JPanel panel = new JPanel();
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private final UserManager userManager;

    /**
     * Creates new form MainFrame
     */
    public SupplyGui(CardLayout layout, JPanel panel) {

        IngredientManager ingredientManager = new IngredientManager();
        PurchaseManager purchaseManager = new PurchaseManager(ingredientManager);

        orderOverviewGUI = new PurchaseOverviewPanel(purchaseManager, ingredientManager, PurchaseOverviewPanel.PresentationType.ORDERS);
        quotationOverviewGUI = new PurchaseOverviewPanel(purchaseManager, ingredientManager, PurchaseOverviewPanel.PresentationType.QUOTATIONS);
        stockGUI = new StockOverviewPanel(ingredientManager);

        userManager = new UserManagerImpl();

        cardLayout = layout;
        cardPanel = panel;

        initComponents();
    }

    public void initComponents() {

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create Top panelsetVerticalGroup(GroupLayout.Group group)
        JPanel headerTop = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel companyname = new JLabel("Eetcafe de Hartige Hap");
        companyname.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add Label to headerTop
        headerTop.add(companyname);

        JPanel headerBottom = new JPanel();
        headerBottom.setLayout(new BoxLayout(headerBottom, BoxLayout.LINE_AXIS));
        headerBottom.setBorder(BorderFactory.createEmptyBorder(0, 25, 10, 25));

        JButton logoutButton = new JButton();
        logoutButton.setText("Uitloggen");

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        // Add Button to headerBottom
        headerBottom.add(Box.createHorizontalGlue());
        headerBottom.add(logoutButton);

        // Add Top Panel to Panel
        panel.add(headerTop);
        panel.add(headerBottom);

        // Create Main panel
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Voorraad beheer", stockGUI);
        tabbedPane.addTab("Inkooporder beheer", orderOverviewGUI);
        tabbedPane.addTab("Inkoopofferte beheer", quotationOverviewGUI);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = tabbedPane.getSelectedIndex();
                switch (index) {
                    case 0:
                        stockGUI.refresh();
                        break;
                    case 1:
                        orderOverviewGUI.refresh();
                        break;
                    case 2:
                        quotationOverviewGUI.refresh();
                        break;
                }
            }
        });

        // Add scrollPane with JTable to mainPanel
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Add mainPanel to Panel
        panel.add(mainPanel);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel poweredby = new JLabel("Powered by InfoSys");
        poweredby.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add Label to Bottom Panel
        bottom.add(poweredby);

        // Add Bottom Panel to Panel
        panel.add(bottom);
    }

    /**
     * Method for handeling the logout button
     *
     * @param evt
     */
    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        userManager.logout();
        cardLayout.show(cardPanel, "login");
    }

    public void refresh() {
        orderOverviewGUI.refresh();
        quotationOverviewGUI.refresh();
    }

    public JPanel getPanel() {
        return panel;
    }
}
