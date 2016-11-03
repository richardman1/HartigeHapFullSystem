/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.orderSystem.domain;

import java.util.ArrayList;
import nl.infosys.hartigehap.orderSystem.databaseacces.OrderDao;
import nl.infosys.hartigehap.orderSystem.databaseacces.ProductDao;

/**
 * @author devc0n
 */
public class Order implements ImmutableOrder {

    private int tableNr;
    private double totalPrice;
    private String state;

    private ArrayList<Product> totalOrder = new ArrayList<>();
    private ArrayList<Product> order = new ArrayList<>();

    private ProductDao productDao = new ProductDao();
    private OrderDao orderDao = new OrderDao();

    public Order() {

    }

    /**
     * Creates Order
     *
     * @param tablenumber
     */
    public Order(int tablenumber) {
        tableNr = tablenumber;
    }

    /**
     * Method to get the table number
     *
     * @return int tableNr
     */
    @Override
    public int getTablenumber() {
        return tableNr;
    }

    /**
     * Method to set the table number
     *
     * @param tablenumber
     */
    @Override
    public void setTablenumber(int tablenumber) {
        tableNr = tablenumber;
    }

    /**
     * Method for getting the totalprice
     *
     * @return double totalprice
     */
    @Override
    public double getTotalprice() {
        return totalPrice;
    }

    /**
     * Method to set the total price
     *
     * @param price
     */
    @Override
    public void setTotalprice(double price) {
        totalPrice = price;
    }

    /**
     * Method to get the state of an order
     *
     * @return String the state of an order
     */
    @Override
    public String getOrderstate() {
        return state;
    }

    /**
     * Method to set the order state
     *
     * @param orderstate
     */
    @Override
    public void setOrderstate(String orderstate) {
        state = orderstate;
    }

    /**
     * Method to get the order list containing product objects
     *
     * @return Arraylist containing product objects
     */
    @Override
    public ArrayList<Product> getOrder() {
        return order;
    }

    /**
     * Method for getting the total order list
     *
     * @return Arraylist containing product objects
     */
    @Override
    public ArrayList<Product> getTotalOrder() {
        return totalOrder;
    }

    /**
     * Method for erasing the order list
     */
    @Override
    public void clearOrder() {
        order.clear();
    }

    /**
     * Method for erasing the total order list
     */
    @Override
    public void clearTotalOrder() {
        totalOrder.clear();
    }

    /**
     * Method to add an product to the order list
     *
     * @param code
     * @param tableNr
     */
    @Override
    public void addProduct(long code, int tableNr) {
        order = productDao.getProduct(code, tableNr);
    }

    /**
     * Method for deleting an product out of the order list
     *
     * @param code
     * @return boolean true or false
     */
    @Override
    public boolean deleteProduct(long code) {

        for (ImmutableProduct orderitem : order) {
            if (code == orderitem.getCode()) {

                int amount = orderitem.getAmount();

                if (amount > 1) {

                    double price = orderitem.getPrice();
                    price = price / amount;
                    amount = amount - 1;
                    price = price * amount;
                    orderitem.setAmount(amount);
                    orderitem.setPrice(price);
                } else {
                    order.remove(orderitem);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if the order status allow to add an product to the order
     * list
     *
     * @param tableNr
     * @return boolean true or false
     */
    @Override
    public boolean addProductCheck(int tableNr) {
        return productDao.addProductCheck(tableNr);
    }

    /**
     * Method for placing an order
     *
     * @param tableNr
     */
    @Override
    public void placeOrder(int tableNr) {

        totalOrder.add(new Product(12345, "Bestelling", 0, 0));
        orderDao.insertOrder(tableNr, order);
        for (Product orderItem : order) {

            totalOrder.add(orderItem);
            totalPrice = totalPrice + (orderItem.getPrice());
        }
    }

}
