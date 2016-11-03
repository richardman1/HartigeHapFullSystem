/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.orderSystem.businesslogic;

import nl.infosys.hartigehap.orderSystem.domain.Product;
import java.util.ArrayList;
import nl.infosys.hartigehap.orderSystem.databaseacces.OrderDao;
import nl.infosys.hartigehap.orderSystem.domain.Order;

/**
 *
 * @author devc0n
 *
 */
public class OrderManagerImpl implements OrderManager {

    private OrderDao orderDao;
    private Order order;

    public OrderManagerImpl() {

        orderDao = new OrderDao();
        order = new Order();

    }

    /**
     * 
     * @return 
     */
    @Override
    public ArrayList<Product> getTotalOrder() {
        return order.getTotalOrder();
    }

    /**
     * 
     * @param code
     * @return 
     */
    @Override
    public boolean deleteProduct(long code) {
        return order.deleteProduct(code);
    }

    /**
     * 
     * @param tableNr 
     */
    @Override
    public void placeOrder(int tableNr) {
        order.placeOrder(tableNr);
    }

    /**
     * 
     * @return 
     */
    @Override
    public double getTotalPrice() {
        return order.getTotalprice();
    }

    /**
     * 
     */
    @Override
    public void clearOrder() {
        order.clearOrder();
    }

    /**
     * 
     */
    @Override
    public void clearTotalOrder() {
        order.clearTotalOrder();
    }

    /**
     * 
     * @param tableNr 
     */
    @Override
    public void checkOut(int tableNr) {
        orderDao.updateCheckOut(tableNr);
    }

    /**
     * 
     * @param code
     * @param tableNr 
     */
    @Override
    public void addProduct(long code, int tableNr) {
        order.addProduct(code, tableNr);
    }

    /**
     * 
     * @param tableNr
     * @return 
     */
    @Override
    public boolean addProductCheck(int tableNr) {
        System.out.println("OrderManagerImp: " + tableNr );
        return order.addProductCheck(tableNr);
    }

    /**
     * 
     * @return 
     */
    @Override
    public ArrayList<Product> getOrder() {
        return order.getOrder();
    }
}
