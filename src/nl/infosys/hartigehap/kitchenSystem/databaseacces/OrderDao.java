package nl.infosys.hartigehap.kitchenSystem.databaseacces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import nl.infosys.hartigehap.kitchenSystem.datastore.DataStore;
import nl.infosys.hartigehap.kitchenSystem.domain.Employee;
import nl.infosys.hartigehap.kitchenSystem.domain.Order;
import nl.infosys.hartigehap.kitchenSystem.domain.OrderDetail;

/**
 *
 * @author Jan Wintermans
 * @author Nick Audenaerde
 */
public class OrderDao {

    private DataStore con;
    private OrderDetail orderDetails;
    private ArrayList<Order> orders;
    private ArrayList<OrderDetail> orderList;

    /**
     * Initializes the databaseconnection.
     */
    public OrderDao() {
        con = new DataStore();
    }

    /**
     * This method selects all coupled employees based on order ID.
     *
     * @param orderId The order id that is coupled to an employee.
     * @return ArrayList with comployees, coupled to a dish.
     */
    public ArrayList<Employee> findAllCoupledEmployees(int orderId) {
        //Open database connection.
        con.openConnection();
        ArrayList<Employee> coupledEmployee = new ArrayList();
        ResultSet rs = con.ExecuteDB(
                "SELECT Naam, MedewerkerID, Voornaam "
                + "FROM Medewerker, BestelRegel, Product, Bestelling "
                + "WHERE Medewerker.MedewerkerID = BestelRegel.fk_MedewerkerID "
                + "AND BestelRegel.fk_BestellingID = Bestelling.BestellingID "
                + "AND BestelRegel.fk_ProductID = Product.ProductID "
                + "AND Medewerker.Functie = 'keukenmedewerker' "
                + "AND (BestelRegel.LeverStatus =  'Besteld' OR BestelRegel.LeverStatus = 'Inverwerking') "
                + "AND Bestelling.BestellingId = " + orderId + "; ");

        if (rs != null) {
            try {
                while (rs.next()) {
                    int employeeId = rs.getInt("MedewerkerID");
                    String dishName = rs.getString("Naam");
                    String firstName = rs.getString("Voornaam");
                    Employee employee = new Employee();
                    employee.setEmployeeId(employeeId);
                    employee.setFirstName(firstName);
                    employee.setDishName(dishName);
                    coupledEmployee.add(employee);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        con.closeConnection();
        return coupledEmployee;
    }

    /**
     * This method finds all kitchen employees.
     *
     * @return ArrayList with kitchen employees.
     */
    public ArrayList<Employee> findAllEmployees() {
        //Open database connection.
        con.openConnection();
        ArrayList<Employee> employeeList = new ArrayList();
        ResultSet rs = con.ExecuteDB("SELECT MedewerkerID, Voornaam  FROM `medewerker` WHERE Functie = 'keukenmedewerker';");
        if (rs != null) {
            try {
                while (rs.next()) {
                    int employeeId = rs.getInt("MedewerkerID");
                    String firstName = rs.getString("VoorNaam");
                    Employee employee = new Employee();
                    employee.setEmployeeId(employeeId);
                    employee.setFirstName(firstName);
                    employeeList.add(employee);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        con.closeConnection();
        return employeeList;
    }

    /**
     * This method finds all order that have to be cooked.
     *
     * @return ArrayList that contains all orders that haven't been prepared
     * yet.
     */
    public ArrayList<Order> findAllOrders() {
        //Open database connection.
        con.openConnection();
        //create a new arraylist in variable orders
        orders = new ArrayList();
        //perform a select query
        ResultSet rs = con.ExecuteDB(
                "SELECT * FROM keukenbestelling");
        if (rs != null) {
            try {
                //loop through every row in the resultset and put the variables 
                //in an Order object
                while (rs.next()) {
                    int orderId = rs.getInt("BestellingID");
                    int tableNumber = rs.getInt("Tafelnummer");
                    Time timeStamp = rs.getTime("TimeStamp");
                    String deliveryStatus = rs.getString("LeverStatus");
                    String accepted = rs.getString("AcceptatieStatus");
                    Order order = new Order();
                    order.setOrderId(orderId);
                    order.setTableNumber(tableNumber);
                    order.setTimeStamp(timeStamp);
                    order.setStatus(deliveryStatus);
                    order.setAccepted(accepted);
                    orders.add(order);
                }
                //show in console if something goes wrong
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        con.closeConnection();
        return orders;
    }

    /**
     * This method finds all order details on a certain order based on order ID.
     *
     * @param orderId Order id that has orders with details.
     * @return ArrayList with orderdetails, based on orderID.
     */
    public ArrayList<OrderDetail> findOrderDetails(int orderId) {
        //Open database connection.
        con.openConnection();
        orderList = new ArrayList();
        ResultSet RS2 = con.ExecuteDB(
                "SELECT * FROM keukenbestellingdetails WHERE BestellingID = " + orderId + " ORDER BY GemiddeldeTijd DESC ;");
        if (RS2 != null) {
            try {
                while (RS2.next()) {

                    //Placing all orders into an Order object
                    int ammount = RS2.getInt("Hoeveelheid");
                    String name = RS2.getString("Naam");
                    String description = RS2.getString("Beschrijving");
                    int averageTime = RS2.getInt("GemiddeldeTijd");
                    int productId = RS2.getInt("ProductID");

                    //geef gegevens door aan entity layer
                    orderDetails = new OrderDetail();
                    orderDetails.setAmmount(ammount);
                    orderDetails.setName(name);
                    orderDetails.setDescription(description);
                    orderDetails.setAverageTime(averageTime);
                    orderDetails.setProductId(productId);
                    orderList.add(orderDetails);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        con.closeConnection();
        return orderList;
    }

    /**
     * This method updates an order with an employee id, so they are coupled.
     *
     * @param employeeId The employee ID that has to make the dish.
     * @param orderId The order ID of the order that needs an employee.
     * @param productId The product ID of the product that has to get made.
     */
    public void updateCoupledOrders(int employeeId, int orderId, int productId) {
        //Open database connection.
        con.openConnection();
        try {
            con.UpdateDB(
                    "UPDATE bestelregel SET fk_MedewerkerID = " + employeeId
                    + " WHERE fk_BestellingID = " + orderId
                    + " AND fk_ProductID = " + productId + ";");
        } catch (Exception e) {
            System.out.println(e);
        }
        con.closeConnection();
    }

    /**
     * This method updates the acception status of an order.
     *
     * @param orderId The order ID that has to be updated.
     * @param status The status of the order.
     */
    public void updateAcceptionStatus(int orderId, String status) {
        //Open database connection.
        con.openConnection();
        try {
            con.UpdateDB(""
                    + "UPDATE bestelling, bestelregel SET AcceptatieStatus = '" + status
                    + "', LeverStatus = 'Inverwerking' "
                    + " WHERE BestellingID = " + orderId
            );
        } catch (Exception e) {
            System.out.println(e);
        }
        con.closeConnection();
    }

    /**
     * This method updates the status of the delivery based on Order ID.
     *
     * @param orderId The order that has to be deliverd.
     */
    public void updateDeliveryStatus(int orderId) {
        //Open database connection.
        con.openConnection();
        try {
            con.UpdateDB(""
                    + " UPDATE bestelregel, bestelling, product "
                    + " SET bestelregel.LeverStatus = 'Gereed' "
                    + " WHERE bestelling.BestellingID = bestelregel.fk_BestellingID "
                    + " AND product.ProductID = bestelregel.fk_ProductID "
                    + " AND bestelling.BestellingID = " + orderId
                    + " AND product.Categorie != 'Drankjes' ;"
            );
        } catch (Exception e) {
            System.out.println(e);
        }
        con.closeConnection();
    }
}
