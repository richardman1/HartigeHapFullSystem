/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.orderSystem.domain;

/**
 *
 * @author Richard de Jongh
 */
public class Product implements ImmutableProduct {

    private long code;
    private double price;
    private String name;
    private int amount;

    /**
     * Creates Product
     *
     * @param productCode the productcode
     * @param productName the product name
     * @param productAmount the amount
     * @param productPrice the price
     */
    public Product(long productCode, String productName, int productAmount, double productPrice) {
        code = productCode;
        name = productName;
        amount = productAmount;
        price = productPrice;

    }

    /**
     * Method to get the product code
     *
     * @return code
     */
    @Override
    public long getCode() {
        return code;
    }

    /**
     * Method to get the price
     *
     * @return price
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * Method to get the amount
     *
     * @return amount
     */
    @Override
    public int getAmount() {
        return amount;
    }

    /**
     * Method to get the name of an product
     *
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Method to set the amount
     *
     * @param productAmount the amount
     */
    @Override
    public void setAmount(int productAmount) {
        amount = productAmount;
    }

    /**
     * Method to set the price
     *
     * @param productPrice the price
     */
    @Override
    public void setPrice(double productPrice) {
        price = productPrice;
    }
}
