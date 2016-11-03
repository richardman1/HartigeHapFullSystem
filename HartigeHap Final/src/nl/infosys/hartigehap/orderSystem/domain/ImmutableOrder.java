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
     * 
     * @return 
     */
    public int getTablenumber();

    /**
     * 
     * @param tablenumber 
     */
    public void setTablenumber(int tablenumber);

    /**
     * 
     * @return 
     */
    public double getTotalprice();

    /**
     * 
     * @param price 
     */
    public void setTotalprice(double price);

    /**
     * 
     * @return 
     */
    public String getOrderstate();

    /**
     * 
     * @param orderstate 
     */
    public void setOrderstate(String orderstate);

    /**
     * 
     * @return 
     */
    public ArrayList<Product> getOrder();

    /**
     * 
     * @return 
     */
    public ArrayList<Product> getTotalOrder();

    /**
     * 
     */
    public void clearOrder();

    /**
     * 
     */
    public void clearTotalOrder();

    /**
     * 
     * @param code
     * @param tableNr 
     */
    public void addProduct(long code, int tableNr);

    /**
     * 
     * @param code
     * @return 
     */
    public boolean deleteProduct(long code);

    /**
     * 
     * @param tableNr
     * @return 
     */
    public boolean addProductCheck(int tableNr);

    /**
     * 
     * @param tableNr 
     */
    public void placeOrder(int tableNr);
}
