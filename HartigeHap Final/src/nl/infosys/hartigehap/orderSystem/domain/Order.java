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

    public Order(int tablenumber) {
        tableNr = tablenumber;
    }

    @Override
    public int getTablenumber() {
        return tableNr;
    }

    @Override
    public void setTablenumber(int tablenumber) {
        tableNr = tablenumber;
    }

    @Override
    public double getTotalprice() {
        return totalPrice;
    }

    @Override
    public void setTotalprice(double price) {
        totalPrice = price;
    }

    @Override
    public String getOrderstate() {
        return state;
    }

    @Override
    public void setOrderstate(String orderstate) {
        state = orderstate;
    }

    @Override
    public ArrayList<Product> getOrder() {
        return order;
    }

    @Override
    public ArrayList<Product> getTotalOrder() {
        return totalOrder;
    }

    @Override
    public void clearOrder() {
        order.clear();
    }

    @Override
    public void clearTotalOrder() {
        totalOrder.clear();
    }

    @Override
    public void addProduct(long code, int tableNr) {
        order = productDao.getProduct(code, tableNr);
    }

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

    @Override
    public boolean addProductCheck(int tableNr) {
        System.out.println("Order: " + tableNr );
        return productDao.addProductCheck(tableNr);
    }

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
