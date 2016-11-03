/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.CardLayout;
import javax.swing.JPanel;
import junit.framework.TestCase;
import nl.infosys.hartigehap.mainSystem.presentation.MainGui;
import nl.infosys.hartigehap.orderSystem.businesslogic.OrderManagerImpl;
import nl.infosys.hartigehap.orderSystem.presentation.OrderGui;
import nl.infosys.hartigehap.userSystem.businesslogic.UserManager;
import nl.infosys.hartigehap.userSystem.businesslogic.UserManagerImpl;

/**
 *
 * @author devc0n
 */
public class OrderGuiTest extends TestCase {

    private UserManager userManager;
    private boolean deleteState;
    private boolean checkOutState;
    private boolean placeOrderState;
    private MainGui mainGui = new MainGui();
    private JPanel cardPanel = new JPanel();
    private CardLayout cardLayout = new CardLayout();
    private OrderGui orderGui = new OrderGui(new OrderManagerImpl(), cardLayout, cardPanel);
    private OrderManagerImpl orderManager = new OrderManagerImpl();
    private String product = "27010006";
    private String bestellen = "27910009";
    private String verwijderen = "27930007";
    private String afrekenen = "27920008";

    public OrderGuiTest(String testName) {
        super(testName);
        mainGui.setVisible(true);
        userManager = new UserManagerImpl();
        String testname = "switch test";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        userManager = new UserManagerImpl();
        userManager.login("1", "1234");
        orderManager.clearOrder();
        orderManager.clearTotalOrder();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCheckCode1() throws Exception {
        setUp();
        System.out.println("testcase 1");
        orderGui.checkCode(product);
        orderGui.checkCode(verwijderen);
        assertFalse(deleteState);
    }

    public void testCheckCode2() {
        System.out.println("testcase 2");
        orderGui.checkCode(product);
        orderGui.checkCode(bestellen);
        assertEquals(placeOrderState, true);
    }

    public void testCheckCode3() {
        System.out.println("testcase 3");
        orderGui.checkCode(product);
        orderGui.checkCode(bestellen);
        orderGui.checkCode(afrekenen);
        assertEquals(checkOutState, true);
    }

    public void testCheckCode4() {
        System.out.println("testcase 4");
        orderGui.checkCode(product);
        assertEquals(orderManager.getOrder().size(), 1);
    }

    public void testCheckCode5() {
        System.out.println("testcase 5");
        orderGui.checkCode(product);
        orderGui.checkCode(bestellen);
        orderGui.checkCode(bestellen);
        assertEquals(placeOrderState, false);
        assertEquals(orderManager.getOrder().size(), 0);
    }

    public void testCheckCode6() {

        System.out.println("testcase 6");

        orderGui.checkCode(product);
        orderGui.checkCode(bestellen);
        orderGui.checkCode(bestellen);
        checkOutState = true;
        orderGui.checkCode(afrekenen);
        assertEquals(checkOutState, false);
        assertEquals(orderManager.getTotalOrder().size(), 0);
    }

    public void testCheckCode7() {

        System.out.println("testcase 7");
        orderGui.checkCode(product);
        deleteState = true;
        orderGui.checkCode(product);
        assertEquals(deleteState, false);
        assertEquals(orderManager.getOrder().size(), 0);
    }
}
