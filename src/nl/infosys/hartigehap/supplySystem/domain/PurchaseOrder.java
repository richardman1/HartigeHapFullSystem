package nl.infosys.hartigehap.supplySystem.domain;

import java.util.ArrayList;
import java.util.List;
import nl.infosys.hartigehap.supplySystem.businesslogic.PurchaseManager.Status;

/**
 *
 * @author Gregor
 */
public class PurchaseOrder implements Interface_PurchaseOrderReadOnly {

    private Interface_SupplierReadOnly supplier;
    private List<PurchaseOrderLine> orderLines;
    private Status status;
    private long timestamp;
    private int id;
    private boolean pendingUpdate;

    /**
     *
     * @param id
     * @param timestamp
     * @param status
     */
    public PurchaseOrder(int id, long timestamp, Status status) {
        this.id = id;
        this.timestamp = timestamp;
        this.status = status;
        orderLines = new ArrayList<>();
        pendingUpdate = false;
    }

    /**
     * Adds a orderline to the order.
     *
     * @param nutrient
     * @param amount
     * @param price
     */
    public void addOrderLine(Interface_IngredientReadOnly nutrient, int amount, double price) {
        orderLines.add(new PurchaseOrderLine(nutrient, amount, price));
    }

    /**
     * Replaces a orderline in the order.
     *
     * @param lineIndex
     * @param nutrient
     * @param amount
     * @param price
     */
    public void replaceOrderline(int lineIndex, Interface_IngredientReadOnly nutrient, int amount, double price) {
        orderLines.set(lineIndex, new PurchaseOrderLine(nutrient, amount, price));
        pendingUpdate = true;
    }

    /**
     * Removes an orderline from the order.
     *
     * @param index
     */
    public void removeOrderLine(int index) {
        orderLines.remove(index);
    }

    /**
     * Gets all orderlines currently in the order.
     *
     * @return
     */
    @Override
    public List<PurchaseOrderLine> getOrderlines() {
        return orderLines;
    }

    /**
     * Get a single orderline, specified with its index number, from the order.
     *
     * @param index
     * @return
     */
    @Override
    public PurchaseOrderLine getOrderLine(int index) {
        return orderLines.get(index);
    }

    /**
     * Sets the supplier for the order.
     *
     * @param supplier
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * gets the supplier for the order.
     *
     * @return
     */
    @Override
    public Interface_SupplierReadOnly getSupplier() {
        return supplier;
    }

    /**
     * Gets the unix timestamp (amount of seconds from 1970) of the createion
     * date for the order.
     *
     * @return
     */
    @Override
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the internal id for this order.
     *
     * @return
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Gets the total price for the order, this is purely a convinience method.
     *
     * @return
     */
    @Override
    public double getTotalPrice() {
        return getTotalPrice(0);
    }

    /**
     * Gets the total price for the order, this is purely a convinience method.
     *
     * @param BTWpercentage The btw percentage.
     * @return
     */
    @Override
    public double getTotalPrice(int BTWpercentage) {
        double price = 0.0;
        double percentage = BTWpercentage / 100;

        for (PurchaseOrderLine orderline : orderLines) {
            price += orderline.getLineTotal();
        }
        price += price * BTWpercentage;

        return price;
    }

    /**
     * Gets the status of the order.
     *
     * @return
     */
    @Override
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the order.
     *
     * @param status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isPendingUpdate() {
        return pendingUpdate;
    }
}
