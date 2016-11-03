package nl.infosys.hartigehap.kitchenSystem.domain;

/**
 * @author Jan Wintermans
 * @author Nick Audenaerde
 */
public class Employee {

    private int employeeId;
    private String firstName;
    private String dishName;

    public Employee() {
    }

    /**
     * This method returns the name of a dish.
     *
     * @return String name of dish.
     */
    public String getDishName() {
        return dishName;
    }

    /**
     * This method sets the name of a dish.
     *
     * @param dishName String name of dish.
     */
    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    /**
     * This method returns the unique number of an employee.
     *
     * @return int id of an employee.
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * This method sets the unique number of an employee.
     *
     * @param employeeId int id of an employee.
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * This method returns the first name of an employee.
     *
     * @return String name of an employee.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This method sets the first name of an employee.
     *
     * @param firstName String first name of an employee.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
