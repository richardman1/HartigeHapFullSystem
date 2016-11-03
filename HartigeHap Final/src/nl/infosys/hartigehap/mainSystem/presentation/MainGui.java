/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.mainSystem.presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import nl.infosys.hartigehap.barSystem.businesslogic.BedieningManagerImpl;
import nl.infosys.hartigehap.barSystem.presentation.BarGui;
import nl.infosys.hartigehap.orderSystem.businesslogic.OrderManagerImpl;
import nl.infosys.hartigehap.orderSystem.presentation.OrderGui;
import nl.infosys.hartigehap.userSystem.businesslogic.UserManagerImpl;
import nl.infosys.hartigehap.userSystem.presentation.UserGui;

/**
 *
 * @author Sander van Belleghem
 */
public class MainGui extends JFrame {

    private JPanel cardPanel = new JPanel();
    private CardLayout cardLayout = new CardLayout();

    public MainGui() {

        initComponents();
        pack();
    }

    private void initComponents() {
        cardPanel.setLayout(cardLayout);

        UserGui userGui = new UserGui(new UserManagerImpl(), cardLayout, cardPanel);
        OrderGui orderGui = new OrderGui(new OrderManagerImpl(), cardLayout, cardPanel);
        BarGui barGui = new BarGui(new BedieningManagerImpl(), cardLayout, cardPanel);
        // KitchenGui kitchenGui = new KitchGui(new BedieningManagerImpl(), cardLayout, cardPanel);
        
        cardPanel.add(userGui.getPanel(), "login");
        cardPanel.add(orderGui.getPanel(), "klant");
        cardPanel.add(barGui.getPanel(), "bediening");
        // cardPanel.add(kitchenGui.getPanel(), "keuken");
        // cardPanel.add(kitchenGui.getPanel(), "chef-kok");
        // cardPanel.add(supplyGui.getPanel(), "inkoper");

        getContentPane().add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, "login");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Set Window to Full screen
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
    }

    public void showPanel(String panel) {
        cardLayout.show(cardPanel, panel);
    }

}
