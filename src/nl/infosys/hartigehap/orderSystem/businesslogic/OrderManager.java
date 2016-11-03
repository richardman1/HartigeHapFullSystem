/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.orderSystem.businesslogic;

import java.util.ArrayList;
import nl.infosys.hartigehap.orderSystem.domain.Product;

/**
 *
 * @author Sander van Belleghem
 */
public interface OrderManager {

    /**
     * Method for deleting an product out of the order list
     *
     * @param code the product code
     * @return boolean true or false
     */
    public boolean deleteProduct(long code);

    /**
     * Method for placing an order
     *
     * @param tableNr the table number
     */
    public void placeOrder(int tableNr);

    /**
     * Method for getting the totalprice
     *
     * @return double totalprice
     */
    public double getTotalPrice();

    /**
     * Method for erasing the order list
     */
    public void clearOrder();

    /**
     * Method for erasing the total order list
     */
    public void clearTotalOrder();

    /**
     * Method for getting the total order list
     *
     * @return Arraylist containing product objects
     */
    public ArrayList<Product> getTotalOrder();

    /**
     * Method to check if the order status allow to add an product to the order
     * list
     *
     * @param tableNr the table number
     * @return boolean true or false
     */
    public boolean addProductCheck(int tableNr);

    /**
     * Method to checkout
     *
     * @param tableNr the table number
     */
    public void checkOut(int tableNr);

    /**
     * Method to add an product to the order list
     *
     * @param code the product code
     * @param tableNr the table number
     */
    public void addProduct(long code, int tableNr);

    /**
     * Method to get the order list containing product objects
     *
     * @return Arraylist containing product objects
     */
    public ArrayList<Product> getOrder();

}
