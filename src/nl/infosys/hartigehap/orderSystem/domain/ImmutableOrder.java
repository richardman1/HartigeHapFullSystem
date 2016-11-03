/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.orderSystem.domain;

import java.util.ArrayList;

/**
 *
 * @author Sander van Belleghem
 */
public interface ImmutableOrder {

    /**
     * Method to get the correct tablenumber of a order.
     *
     * @return tablenumber.
     */
    public int getTablenumber();

    /**
     * Method to set the tablenumber of a order.
     *
     * @param tablenumber
     */
    public void setTablenumber(int tablenumber);

    /**
     * Method to get the Total price of a order.
     *
     * @return totalPrice
     */
    public double getTotalprice();

    /**
     * Method to set the total price of a order.
     *
     * @param price
     */
    public void setTotalprice(double price);

    /**
     * Method to get the state of a order.
     *
     * @return orderState
     */
    public String getOrderstate();

    /**
     * Method to set the state of a order.
     *
     * @param orderstate
     */
    public void setOrderstate(String orderstate);

    /**
     * Method to get the total order with a list of the ordered products.
     *
     * @return order
     */
    public ArrayList<Product> getOrder();

    /**
     * Method to get the total order consisting all the orders a customer had
     * placed in an evening.
     *
     * @return totalorderlist
     */
    public ArrayList<Product> getTotalOrder();

    /**
     * Method to clear a order so it doesn't contain anything anymore.
     *
     */
    public void clearOrder();

    /**
     * Method to clear the total overview of orders so it doesn't contain
     * anything anymore.
     *
     */
    public void clearTotalOrder();

    /**
     * Method to add a product to a order.
     *
     * @param code
     * @param tableNr
     */
    public void addProduct(long code, int tableNr);

    /**
     * Method to delete a product from a order.
     *
     * @param code
     * @return
     */
    public boolean deleteProduct(long code);

    /**
     * Method to check if an order has been paid.
     *
     * @param tableNr
     * @return
     */
    public boolean addProductCheck(int tableNr);

    /**
     * Method to place the order.
     *
     * @param tableNr
     */
    public void placeOrder(int tableNr);
}
