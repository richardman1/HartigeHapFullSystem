package nl.infosys.hartigehap.kitchenSystem.domain;

import java.sql.Time;

/**
 *
 * @author Jan Wintermans
 * @author Nick Audenaerde
 */
public class Order {

    private int orderId;
    private int tableNumber;
    private Time timeStamp;
    private String status;
    private String accepted;

    public Order() {
    }

    /**
     * This method returns the unique number of an order.
     *
     * @return int unique number of an order.
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * This method returns the corresponding tablenumber of an order.
     *
     * @return int tablenumber of an order.
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * This method returns the date and time of when the order was initialized.
     *
     * @return Time date and time of initiation.
     */
    public Time getTimeStamp() {
        return timeStamp;
    }

    /**
     * This method returns the dilivery status of an order.
     *
     * @return String dilivery status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method either returns that this order has been accepted or not
     * accepted.
     *
     * @return String accepted or not.
     */
    public String getAccepted() {
        if (accepted.equals("Geaccepteerd")) {
            return "Ja";
        } else {
            return "Nee";
        }
    }

    /**
     * This method sets the acception status to accepted or not accepted.
     *
     * @param accepted String shows wether accepted or not.
     */
    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    /**
     * This method sets the member orderId to the corresponding number.
     *
     * @param orderId int the unique number of an order.
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * This method sets the tablenumber where this order has to be delivered.
     *
     * @param tableNumber int number of the table.
     */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    /**
     * This method sets the time that the order was created.
     *
     * @param timeStamp Time initiation time of order.
     */
    public void setTimeStamp(Time timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * This method sets the current status of an order.
     *
     * @param status String current status of order.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
