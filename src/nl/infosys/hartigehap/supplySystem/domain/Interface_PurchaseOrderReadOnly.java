/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.supplySystem.domain;

import java.util.List;
import nl.infosys.hartigehap.supplySystem.businesslogic.PurchaseManager.Status;

/**
 *
 * @author Gregor
 */
public interface Interface_PurchaseOrderReadOnly {

    /**
     * Gets all orderlines currently in the order.
     *
     * @return
     */
    public List<PurchaseOrderLine> getOrderlines();

    /**
     * Get a single orderline, specified with its index number, from the order.
     *
     * @param index
     * @return
     */
    public PurchaseOrderLine getOrderLine(int index);

    /**
     * gets the supplier for the order.
     *
     * @return
     */
    public Interface_SupplierReadOnly getSupplier();

    /**
     * Gets the unix timestamp (amount of seconds from 1970) of the createion
     * date for the order.
     *
     * @return
     */
    public long getTimestamp();

    /**
     * Gets the database id for this order.
     *
     * @return
     */
    public int getId();

    /**
     * Gets the total price for the order, this is purely a convinience method.
     *
     * @return
     */
    public double getTotalPrice();

    /**
     * Gets the total price for the order, this is purely a convinience method.
     *
     * @param BTWpercentage The btw percentage.
     * @return
     */
    public double getTotalPrice(int BTWpercentage);

    /**
     * Gets the status of the order.
     *
     * @return
     */
    public Status getStatus();
}
