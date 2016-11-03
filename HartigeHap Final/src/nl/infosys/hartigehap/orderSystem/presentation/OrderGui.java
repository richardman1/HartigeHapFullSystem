/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.orderSystem.presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import nl.infosys.hartigehap.orderSystem.businesslogic.OrderManager;
import nl.infosys.hartigehap.userSystem.businesslogic.UserManagerImpl;
import nl.infosys.hartigehap.orderSystem.domain.ImmutableProduct;
import nl.infosys.hartigehap.orderSystem.domain.Product;
import nl.infosys.hartigehap.userSystem.businesslogic.UserManager;

/**
 *
 * @author Sander van Belleghem
 */
public class OrderGui {

    private final OrderManager orderManager;
    private final UserManager userManager;
    private final JPanel panel = new JPanel();
    private final JTextField inputField = new JTextField(3);
    private final JTable totalList = new JTable();
    private final JTable orderList = new JTable();
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JScrollPane scrollPane = new JScrollPane();
    private final JScrollPane scrollPane2 = new JScrollPane();
    private final JLabel subTotal = new JLabel();
    private int delay = 1000; //milliseconds
    private int finalDelay; //seconden
    private String message;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private boolean deleteState;
    private boolean checkOutState;
    private boolean placeOrderState;

    /**
     * Creates BestelGui
     *
     * @param orderManagerObj
     * @param layout
     * @param panel
     */
    public OrderGui(OrderManager orderManagerObj, CardLayout layout, JPanel panel) {

        orderManager = orderManagerObj;

        userManager = new UserManagerImpl();

        cardLayout = layout;
        cardPanel = panel;
        deleteState = false;

        initComponents();
    }

    /**
     * Method for initializing the Gui elements
     */
    private void initComponents() {

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create Top panelsetVerticalGroup(GroupLayout.Group group)
        JPanel headerTop = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel companyname = new JLabel("Eetcafe Hartige Hap");
        companyname.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add Label to headerTop
        headerTop.add(companyname);

        JPanel headerBottom = new JPanel();
        headerBottom.setLayout(new BoxLayout(headerBottom, BoxLayout.LINE_AXIS));
        headerBottom.setBorder(BorderFactory.createEmptyBorder(0, 20, 5, 20));

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

        // Create new scrollPane for the table
        scrollPane.setViewportView(createTotalList());
        scrollPane2.setViewportView(createOrderList());

        // Add scrollPane with JTable to mainPanel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(scrollPane2, BorderLayout.EAST);
        mainPanel.add(subTotal, BorderLayout.SOUTH);

        // Add mainPanel to Panel
        panel.add(mainPanel);

        // Create Bottom panel
        JPanel inputfieldBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Add Inputfield to inputfieldBottom
        inputfieldBottom.add(inputField);

        // Request the focus on the inputfield
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                inputField.requestFocusInWindow();
            }
        });

        inputField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFieldActionPerformed(evt);
            }
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel poweredby = new JLabel("Powered by InfoSys");
        poweredby.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Add Label to Bottom Panel
        bottom.add(poweredby);

        // Add Inputfield & Bottom Panel to Panel
        panel.add(inputfieldBottom);
        panel.add(bottom);
    }

    /**
     * Method for handeling the logout button
     * @param evt
     */
    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        userManager.logout();
        cardLayout.show(cardPanel, "login");
    }

    /**
     * Method for handeling the inputfield
     * @param evt
     */
    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {

        if (inputField.getText().length() > 0) {
            // Data versturen naar manager klasse voor database afhandeling
            checkCode(inputField.getText());
        }
        inputField.setText("");
    }

    /**
     * Method for displaying the totalOrder list
     * @return JTable for displaying the totalOrder list
     */
    public JTable createTotalList() {

        ArrayList<Product> totalOrder = orderManager.getTotalOrder();

        totalList.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Bestelling", "Aantal", "Subtotaal"
                })
        );

        totalList.setEnabled(false);

        totalList.getTableHeader().setReorderingAllowed(false);

        DefaultTableModel model = (DefaultTableModel) totalList.getModel();
        NumberFormat getallenOpmaker = new DecimalFormat("###,##0.00");
        if (totalOrder.size() > 0) {
            for (ImmutableProduct orderitem : totalOrder) {

                String name = orderitem.getName();

                String price = getallenOpmaker.format(orderitem.getPrice());
                int amount = orderitem.getAmount();

                model.addRow(new Object[]{name /* Productname */, amount /* Amount */, price /* Price each */});
            }
            subTotal.setText("Totaalprijs: " + Currency.getInstance("EUR").getSymbol(Locale.FRANCE) + getallenOpmaker.format(orderManager.getTotalPrice()));
        }

        return totalList;
    }

    /**
     * Method for displaying the order list
     * @return JTable for displaying the order list
     */
    public JTable createOrderList() {

        ArrayList<Product> orders = orderManager.getOrder();

        orderList.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Bestelling", "Aantal", "Subtotaal"
                })
        );

        orderList.setEnabled(false);

        orderList.getTableHeader().setReorderingAllowed(false);

        DefaultTableModel model = (DefaultTableModel) orderList.getModel();

        if (orders.size() > 0) {
            for (ImmutableProduct orderitem : orders) {

                String name = orderitem.getName();
                NumberFormat getallenOpmaker = new DecimalFormat("###,##0.00");
                String price = getallenOpmaker.format(orderitem.getPrice());
                int amount = orderitem.getAmount();

                model.addRow(new Object[]{name /* Productnam */, amount /* Amount */, Currency.getInstance("EUR").getSymbol(Locale.FRANCE) + price /* Price each */});
            }
        }

        return orderList;
    }

    /**
     *
     */
    public void updateTable() {
        scrollPane.setViewportView(createOrderList());
        scrollPane2.setViewportView(createTotalList());
    }

    /**
     * Method for handeling the scanned code
     * @param barcode
     */
    public void checkCode(String barcode) {

        int tableNr = Integer.parseInt(barcode.substring(0, 2));
        String productCode = barcode.substring(2);

        long code = Long.parseLong(productCode);

        if (!deleteState && !placeOrderState && !checkOutState) {
            switch (productCode) {
                case "930007":
                    requestDeleteProduct();
                    break;
                case "910009":
                    requestPlaceOrder();
                    break;
                case "920008":
                    requestCheckout();
                    break;
                default:
                    addProduct(tableNr, code);
                    break;
            }
        } else {
            switch (productCode) {
                case "910009":
                    placeOrder(tableNr);

                    break;
                case "920008":
                    checkout(tableNr);
                    break;
                default:
                    deleteProduct(code);
            }
            checkOutState = false;
            placeOrderState = false;
            deleteState = false;
        }
    }

    /**
     * Method for showing an confirmation window to delete an product out of the order list
     */
    public void requestDeleteProduct() {
        if (!orderManager.getOrder().isEmpty()) {
            deleteState = true;
            showConfirmationWindow("U kunt nu het gewenste product dat u wil verwijderen scannen.<br />Wilt u annuleren, scan dan nogmaals de 'verwijderbarcode'.", 0);
        } else {
            showConfirmationWindow("U heeft nog geen producten gekozen.", 5);
        }
    }

    /**
     * Method for showing an confirmation window to place an order
     */
    public void requestPlaceOrder() {
        if (!orderManager.getOrder().isEmpty()) {
            placeOrderState = true;
            showConfirmationWindow("U heeft aangegeven dat u wilt bestellen.<br />Scan nog een keer 'bestellen' om dit te bevestigen.", 0);
        } else {
            showConfirmationWindow("U heeft nog geen producten gekozen.", 10);
        }
    }

    /**
     * Method for showing an confirmation window for checkout
     */
    public void requestCheckout() {
        if (orderManager.getOrder().isEmpty() && orderManager.getTotalOrder().size() > 0) {
            checkOutState = true;
            showConfirmationWindow("U hebt aangegeven dat u wilt afrekenen.<br />Scan nogmaals 'afrekenen' om dit te bevestigen.", 0);
        } else {
            showConfirmationWindow("Er kan momenteel niet afgerekend worden.", 10);
        }
    }

    /**
     * Method for adding an product to the order list
     * @param tableNr
     * @param code
     */
    public void addProduct(int tableNr, long code) {
        if (orderManager.addProductCheck(tableNr)) {
            showConfirmationWindow("Er dient eerst betaald worden alvorens u opnieuw kunt bestellen.", 10);
        } else {
            orderManager.addProduct(code, tableNr);
            updateTable();
        }
    }

    /**
     * Method for placing the order
     * @param tableNr
     */
    public void placeOrder(int tableNr) {
        if (placeOrderState) {
            orderManager.placeOrder(tableNr);
            orderManager.clearOrder();
            updateTable();
        }
    }

    /**
     * Method to checkout
     * @param tableNr
     */
    public void checkout(int tableNr) {
        if (checkOutState) {
            orderManager.checkOut(tableNr);
            showConfirmationWindow("U hebt aangegeven dat u wilt afrekenen.<br />Een medewerker is onderweg.", 10);
            orderManager.clearTotalOrder();
            updateTable();
            subTotal.setText("");
        }
    }

    /**
     * Method for deleting an product out of the order list
     * @param code
     */
    public void deleteProduct(long code) {
        if (deleteState) {
            if (orderManager.deleteProduct(code)) {
                updateTable();
            }
        }
    }

    /**
     * Method for showing an confirmation window
     * @param text the message to display
     * @param time the time the window must be shown
     */
    public void showConfirmationWindow(String text, int time) {
        finalDelay = time;
        // Set message global
        message = text;
        final JLabel label;

        if (finalDelay != 0) {
            label = new JLabel("<html><body><center><p>" + message + "</p><p></p><p><small>Dit bericht sluit automatisch na: " + finalDelay + " seconden.</small></p></center></body></html>", JLabel.LEFT);
            label.setFont(new Font("DigifaceWide Regular", Font.PLAIN, 18));

            Timer t = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (finalDelay <= 1) {

                        subTotal.setText("");
                        updateTable();

                        JOptionPane.getRootFrame().dispose();
                    }
                    finalDelay--;
                    label.setText("<html><body><center><p>" + message + "</p><p></p><p><small>Dit bericht sluit automatisch na: " + finalDelay + " seconden.</small></p></center></body></html>");

                }
            });
            t.setRepeats(true);
            t.start();

            JOptionPane.showOptionDialog(null, label, "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
            t.stop();
        } else {
            label = new JLabel("<html><body><center><p>" + message + "</p></center><br/></body></html>", JLabel.LEFT);
            label.setFont(new Font("DigifaceWide Regular", Font.PLAIN, 18));

            String s = (String) JOptionPane.showInputDialog(null, label, "Message", JOptionPane.PLAIN_MESSAGE, null, null, null);
            if ((s != null) && (s.length() > 0)) {
                checkCode(s);
            }
        }

    }

    /**
     * Method for getting the OrderGui
     * @return JPanel which contains the OrderGui
     */
    public JPanel getPanel() {
        return panel;
    }
}
