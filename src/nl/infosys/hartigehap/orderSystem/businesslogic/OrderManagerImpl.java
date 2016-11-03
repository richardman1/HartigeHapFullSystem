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

    /**
     * Creates OrderManagerImpl
     */
    public OrderManagerImpl() {

        orderDao = new OrderDao();
        order = new Order();

    }

    /**
     * Method to get the order list containing product objects
     *
     * @return Arraylist containing product objects
     */
    @Override
    public ArrayList<Product> getOrder() {
        return order.getOrder();
    }

    /**
     * Method for getting the total order list
     *
     * @return Arraylist containing product objects
     */
    @Override
    public ArrayList<Product> getTotalOrder() {
        return order.getTotalOrder();
    }

    /**
     * Method for deleting an product out of the order list
     *
     * @param code the productcode
     * @return boolean true or false
     */
    @Override
    public boolean deleteProduct(long code) {
        return order.deleteProduct(code);
    }

    /**
     * Method for placing an order
     *
     * @param tableNr the table number
     */
    @Override
    public void placeOrder(int tableNr) {
        order.placeOrder(tableNr);
    }

    /**
     * Method for getting the totalprice
     *
     * @return double totalprice
     */
    @Override
    public double getTotalPrice() {
        return order.getTotalprice();
    }

    /**
     * Method for erasing the order list
     */
    @Override
    public void clearOrder() {
        order.clearOrder();
    }

    /**
     * Method for erasing the total order list
     */
    @Override
    public void clearTotalOrder() {
        order.clearTotalOrder();
    }

    /**
     * Method to checkout
     *
     * @param tableNr the table number
     */
    @Override
    public void checkOut(int tableNr) {
        orderDao.updateCheckOut(tableNr);
    }

    /**
     * Method to add an product to the order list
     *
     * @param code the productcode
     * @param tableNr the table number
     */
    @Override
    public void addProduct(long code, int tableNr) {
        order.addProduct(code, tableNr);
    }

    /**
     * Method to check if the order status allow to add an product to the order
     * list
     *
     * @param tableNr the table number
     * @return boolean true or false
     */
    @Override
    public boolean addProductCheck(int tableNr) {
        return order.addProductCheck(tableNr);
    }
}
