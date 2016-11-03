package nl.infosys.hartigehap.kitchenSystem.presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import nl.infosys.hartigehap.kitchenSystem.businesslogic.OrderManager;
import nl.infosys.hartigehap.kitchenSystem.domain.Employee;
import nl.infosys.hartigehap.kitchenSystem.domain.Order;
import nl.infosys.hartigehap.kitchenSystem.domain.OrderDetail;
import nl.infosys.hartigehap.userSystem.businesslogic.UserManager;
import nl.infosys.hartigehap.userSystem.businesslogic.UserManagerImpl;

/**
 * @author Sander van Belleghem
 * @author Stefan du Bois
 * @author Olivier Sweep
 * @author Jan Wintermans
 * @author Nick Audenaerde
 */
public class KitchenGui extends javax.swing.JFrame {

    private OrderManager manager;
    private int orderId;
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<OrderDetail> orders = new ArrayList<>();
    private ArrayList<Order> allOrders = new ArrayList<>();
    JMenuBar menuBar;
    JMenu menu, submenu;
    private int selectedRow;
    private DefaultTableModel model;
    private final JPanel panel = new JPanel();
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private final UserManager userManager;

    /**
     * Creates new form MainJFrame Changes the language of the cancel, no, ok
     * and yes buttons.
     *
     * @param manager ordermanager the manager initialized
     */
    public KitchenGui(OrderManager manager, CardLayout layout, JPanel panel) {
        this.manager = manager;

        userManager = new UserManagerImpl();

        initComponents();
        buildGui();

        cardLayout = layout;
        cardPanel = panel;

        UIManager.put("OptionPane.cancelButtonText", "Annuleer");
        UIManager.put("OptionPane.noButtonText", "Nee");
        UIManager.put("OptionPane.okButtonText", "Ok");
        UIManager.put("OptionPane.yesButtonText", "Ja");

        selectedRow = -1;
        
        for (Employee employee : manager.getEmployeeList()) {
            medewerkerComboBox.addItem(employee.getEmployeeId() + ". " + employee.getFirstName());
            employees.add(employee);
        }
    }

    /**
     * Refreshes all orders in the list.
     */
    public void refreshOrders() {
        DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Bestelling nummer", "Tafelnummer", "Tijd", "Status", "Geaccepteerd"
                });

        //import the arraylist orders using the manager
        allOrders = manager.getOrders();

        //loop through the arraylist, let each Order object perform 
        //the getBestellingID() and getTafelNummer and add it to the
        //model of the jTable in the gui
        for (int i = 0; i < allOrders.size(); i++) {
            model.addRow(new Object[]{
                allOrders.get(i).getOrderId(),
                allOrders.get(i).getTableNumber(),
                allOrders.get(i).getTimeStamp(),
                allOrders.get(i).getStatus(),
                allOrders.get(i).getAccepted()
            });
        }
        this.model = model;
        orderTable.setModel(model);
    }

    /**
     * Fills the coupled employee list, and put them in an text area Coupled to
     * a dish.
     */
    private void fillCoupledEmployeeList() {
        String coupledText = "";
        for (Employee employee : manager.getCoupledEmployeeList(orderId)) {
            coupledText += (employee.getEmployeeId() + ". " + employee.getFirstName() + "\n"
                    + "Gerecht " + employee.getDishName() + "\n\n ");
        }
        coupleTextArea.setText(coupledText);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderDetailsField = new javax.swing.JTextArea();
        bestellingButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        coupleTextArea = new javax.swing.JTextArea();
        koppelButton = new javax.swing.JButton();
        medewerkerComboBox = new javax.swing.JComboBox();
        gerechtComboBox = new javax.swing.JComboBox();
        acceptButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        orderReadyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(234, 234, 234));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(95, 650));

        orderDetailsField.setColumns(20);
        orderDetailsField.setRows(5);
        orderDetailsField.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 2, 1, 0));
        jScrollPane1.setViewportView(orderDetailsField);

        bestellingButton.setText("Haal bestellingen op");
        bestellingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bestellingButtonActionPerformed(evt);
            }
        });

        coupleTextArea.setColumns(20);
        coupleTextArea.setRows(5);
        coupleTextArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 2, 1, 0));
        jScrollPane4.setViewportView(coupleTextArea);

        koppelButton.setText("Koppel");
        koppelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                koppelButtonActionPerformed(evt);
            }
        });

        medewerkerComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medewerkerComboBoxActionPerformed(evt);
            }
        });

        gerechtComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerechtComboBoxActionPerformed(evt);
            }
        });

        acceptButton.setText("Accepteren");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        refreshOrders();
        //apply model to table
        orderTable.setModel(model);
        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(orderTable);

        orderReadyButton.setText("Bestelling gereed");
        orderReadyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderReadyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(bestellingButton)
                        .addGap(18, 18, 18)
                        .addComponent(acceptButton)
                        .addGap(18, 18, 18)
                        .addComponent(orderReadyButton)))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(medewerkerComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(koppelButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gerechtComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bestellingButton)
                    .addComponent(acceptButton)
                    .addComponent(orderReadyButton)
                    .addComponent(gerechtComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(medewerkerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(koppelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4))
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1076, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 529, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This methods activates after clicking on the bestelling gereed button. It
     * wil then change the status of the order to ready.
     *
     * @param evt
     */
    private void orderReadyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderReadyButtonActionPerformed
        disable(orderReadyButton, 500);
        if (selectedRow != -1) {
            for (Order order : allOrders) {
                if (order.getOrderId() == orderId) {
                    if (order.getAccepted().equals("Ja")) {
                        if (JOptionPane.showConfirmDialog(null, "Weet je zeker dat de bestelling klaar is?", "Waarschuwing",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            manager.updateDeliveryStatus(orderId);
                            refreshOrders();
                        } else {
                            JOptionPane optionPane = new JOptionPane("De bestelling is niet doorgevoerd", JOptionPane.INFORMATION_MESSAGE);
                            JDialog dialog = optionPane.createDialog("Bestelling niet gereed");
                            dialog.setAlwaysOnTop(this.isAlwaysOnTopSupported());
                            dialog.setVisible(true);
                        }
                    } else if (JOptionPane.showConfirmDialog(null, "De bestelling is niet geaccepteerd, wil je deze nu accepteren?", "Waarschuwing",
                            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        manager.updateAcceptionStatus(orderId, "Geaccepteerd");
                        manager.updateDeliveryStatus(orderId);
                        refreshOrders();
                    }
                }
            }
        } else {
            JOptionPane optionPane = new JOptionPane("Er is geen bestelling geselecteerd.", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Fout bij gereed melden");
            dialog.setAlwaysOnTop(this.isAlwaysOnTopSupported());
            dialog.setVisible(true);
        }
        orderTable.setModel(model);
    }//GEN-LAST:event_orderReadyButtonActionPerformed

    /**
     * When clicked on an order in the list, this method fills the order details
     * of that order, and adds employees and the dishes to the comboxes.
     *
     * @param evt
     */
    private void orderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTableMouseClicked
        coupleTextArea.setText("");
        gerechtComboBox.removeAllItems();

        this.selectedRow = orderTable.getSelectedRow();
        this.orderId = (int) orderTable.getValueAt(selectedRow, 0);

        fillCoupledEmployeeList();

        String allText = "";

        orders.clear();
        for (OrderDetail order : manager.getOrderDetails(orderId)) {
            allText += "Naam: " + order.getName() + "\nBeschrijving:\n" + order.getDescription() + "\nBereidingstijd: " + order.getAverageTime() + " Minuten\n";
            allText += "Hoeveelheid: " + order.getAmmount() + "\n\n";
            gerechtComboBox.addItem(order.getName());
            orders.add(order);
        }
        orderDetailsField.setText(allText);

        orderDetailsField.setEditable(false);

        orderDetailsField.setEditable(false);
        coupleTextArea.setEditable(false);

        
    }//GEN-LAST:event_orderTableMouseClicked

    /**
     * This method accepts an order, through the button, accepteer. Then
     * refreshes the orderlist.
     *
     * @param evt
     */
    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        if (selectedRow != -1) {
            manager.updateAcceptionStatus(orderId, "Geaccepteerd");
            disable(acceptButton, 500);
        } else {
            JOptionPane optionPane = new JOptionPane("Er is geen bestelling geselecteerd.", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Fout bij accepteren");
            dialog.setAlwaysOnTop(this.isAlwaysOnTopSupported());
            dialog.setVisible(true);
        }
        refreshOrders();
    }//GEN-LAST:event_acceptButtonActionPerformed

    private void gerechtComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerechtComboBoxActionPerformed

    }//GEN-LAST:event_gerechtComboBoxActionPerformed

    private void medewerkerComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medewerkerComboBoxActionPerformed

    }//GEN-LAST:event_medewerkerComboBoxActionPerformed

    /**
     * This method couples a dish to a employee, and updates the database.
     *
     * @param evt
     */
    private void koppelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_koppelButtonActionPerformed
        if (selectedRow != -1) {
            int employeeId = employees.get(medewerkerComboBox.getSelectedIndex()).getEmployeeId();
            int productId = orders.get(gerechtComboBox.getSelectedIndex()).getProductId();

            manager.updateCoupledOrderIdOp(employeeId, orderId, productId);
            fillCoupledEmployeeList();
            disable(koppelButton, 500);
        } else {

            JOptionPane optionPane = new JOptionPane("Er is geen bestelling geselecteerd.", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Fout bij koppelen");
            dialog.setAlwaysOnTop(this.isAlwaysOnTopSupported());
            dialog.setVisible(true);
        }
    }//GEN-LAST:event_koppelButtonActionPerformed

    /*
     * This method refreshed the order list.
     */
    private void bestellingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bestellingButtonActionPerformed
        refreshOrders();
    }//GEN-LAST:event_bestellingButtonActionPerformed

    /**
     * This method is a fail safe incase of button mashing.
     */
    static void disable(final AbstractButton b, final long ms) {
        b.setEnabled(false);
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                Thread.sleep(ms);
                return null;
            }

            @Override
            protected void done() {
                b.setEnabled(true);
            }
        }.execute();
    }

    public JPanel getPanel() {
        return panel;
    }

    public JPanel getPanelComponents() {
        return jPanel2;
    }

    public void buildGui() {

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

        // Add scrollPane with JTable to mainPanel
        mainPanel.add(getPanelComponents(), BorderLayout.CENTER);

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JButton bestellingButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextArea coupleTextArea;
    private javax.swing.JComboBox gerechtComboBox;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton koppelButton;
    private javax.swing.JComboBox medewerkerComboBox;
    private javax.swing.JTextArea orderDetailsField;
    private javax.swing.JButton orderReadyButton;
    private javax.swing.JTable orderTable;
    // End of variables declaration//GEN-END:variables
}
