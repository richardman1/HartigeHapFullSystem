/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.orderSystem.domain;

/**
 *
 * @author Sander van Belleghem
 */
public interface ImmutableProduct {

    /**
     * Method to get the product code
     *
     * @return long code
     */
    public long getCode();

    /**
     * Method to get the price
     *
     * @return double price
     */
    public double getPrice();

    /**
     * Method to get the amount
     *
     * @return int amount
     */
    public int getAmount();

    /**
     * Method to get the product name
     *
     * @return String name
     */
    public String getName();

    /**
     * Method to set the amount
     *
     * @param productAmount
     */
    public void setAmount(int productAmount);

    /**
     * Method to set the price
     *
     * @param productPrice
     */
    public void setPrice(double productPrice);

}
