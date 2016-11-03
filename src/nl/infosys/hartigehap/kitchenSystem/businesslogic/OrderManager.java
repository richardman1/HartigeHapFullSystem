package nl.infosys.hartigehap.kitchenSystem.businesslogic;

import java.util.ArrayList;
import nl.infosys.hartigehap.kitchenSystem.domain.Employee;
import nl.infosys.hartigehap.kitchenSystem.domain.Order;
import nl.infosys.hartigehap.kitchenSystem.domain.OrderDetail;

/**
 *
 * @author Jan Wintermans
 * @author Nick Audenaerde
 */
public interface OrderManager {

    /**
     * This method returns an arrayList with Order objects
     *
     * @return ArrayList with all orders.
     */
    public ArrayList<Order> getOrders();

    /**
     * This method returns an arrayList with Orderdetails objects
     *
     * @param orderId The id of the order that has been made.
     * @return ArrayList with all orderdetails.
     */
    public ArrayList<OrderDetail> getOrderDetails(int orderId);

    /**
     * This method returns an arrayList of employees.
     *
     * @return ArrayList with all kitchen employees.
     */
    public ArrayList<Employee> getEmployeeList();

    /**
     * This method returns an arrayList of coupled employees.
     *
     * @param orderId The id of the order that has been made.
     * @return ArrayList with couples employees.
     */
    public ArrayList<Employee> getCoupledEmployeeList(int orderId);

    /**
     * This method updates the coupled dish to the employee
     *
     * @param employeeId The employee that have to make the dish.
     * @param orderId The order Id of the order.
     * @param productId The product id that is in the order.
     */
    public void updateCoupledOrderIdOp(int employeeId, int orderId, int productId);

    /**
     * This method updates the acception status.
     *
     * @param orderId The order id of the order that have to be updated.
     * @param status The status of the order.
     */
    public void updateAcceptionStatus(int orderId, String status);

    /**
     * This method updates the deliverty status of an order.
     *
     * @param order The order that has to be updated.
     */
    public void updateDeliveryStatus(int order);
}
