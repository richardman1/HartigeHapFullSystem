package nl.infosys.hartigehap.kitchenSystem.businesslogic;

import java.util.ArrayList;
import nl.infosys.hartigehap.kitchenSystem.databaseacces.OrderDao;
import nl.infosys.hartigehap.kitchenSystem.domain.Employee;
import nl.infosys.hartigehap.kitchenSystem.domain.Order;
import nl.infosys.hartigehap.kitchenSystem.domain.OrderDetail;

/**
 * @author Jan Wintermans
 * @author Nick Audenaerde
 */
public class OrderManagerImpl implements OrderManager {

    /**
     * OrderDao object which contains an order data list.
     */
    private OrderDao orderDataList;
    /**
     * OrderDao object which contains order details.
     */
    private OrderDao orderDetails;

    /**
     * Initializes the OrderDao.
     */
    public OrderManagerImpl() {
        orderDataList = new OrderDao();
        orderDetails = new OrderDao();
    }

    /**
     * This method returns an arrayList with Order objects
     *
     * @return ArrayList with all orders.
     */
    @Override
    public ArrayList<Order> getOrders() {
        return orderDataList.findAllOrders();
    }

    /**
     * This method returns an arrayList with Orderdetails objects
     *
     * @param orderId The id of the order that has been made.
     * @return ArrayList with all orderdetails.
     */
    @Override
    public ArrayList<OrderDetail> getOrderDetails(int orderId) {
        return orderDetails.findOrderDetails(orderId);
    }

    /**
     * This method returns an arrayList of employees.
     *
     * @return ArrayList with all kitchen employees.
     */
    @Override
    public ArrayList<Employee> getEmployeeList() {
        return orderDataList.findAllEmployees();
    }

    /**
     * This method returns an arrayList of coupled employees.
     *
     * @param orderId The id of the order that has been made.
     * @return ArrayList with couples employees.
     */
    public ArrayList<Employee> getCoupledEmployeeList(int orderId) {
        return orderDataList.findAllCoupledEmployees(orderId);
    }

    /**
     * This method updates the coupled dish to the employee
     *
     * @param employeeId The employee that have to make the dish.
     * @param orderId The order Id of the order.
     * @param productId The product id that is in the order.
     */
    @Override
    public void updateCoupledOrderIdOp(int employeeId, int orderId, int productId) {
        orderDataList.updateCoupledOrders(employeeId, orderId, productId);
    }

    /**
     * This method updates the acception status.
     *
     * @param orderId The order id of the order that have to be updated.
     * @param status The status of the order.
     */
    @Override
    public void updateAcceptionStatus(int orderId, String status) {
        orderDataList.updateAcceptionStatus(orderId, status);
    }

    /**
     * This method updates the deliverty status of an order.
     *
     * @param order The order that has to be updated.
     */
    @Override
    public void updateDeliveryStatus(int order) {
        orderDataList.updateDeliveryStatus(order);
    }
}
