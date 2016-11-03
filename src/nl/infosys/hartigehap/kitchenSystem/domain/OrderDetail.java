package nl.infosys.hartigehap.kitchenSystem.domain;

/**
 * @author Jan Wintermans
 * @author Nick Audenaerde
 */
public class OrderDetail {

    private int ammount;
    private String name;
    private String description;
    private int averageTime;
    private int productId;

    /**
     * Initializes the OrderDetail.
     */
    public OrderDetail() {

    }

    /**
     * This method gets the ammount of orders.
     *
     * @return int order ammount.
     */
    public int getAmmount() {
        return ammount;
    }

    /**
     * This method gets the name of an product.
     *
     * @return String name of product.
     */
    public String getName() {
        return name;
    }

    /**
     * This method gets the product ID.
     *
     * @return int product id.
     */
    public int getProductId() {
        return productId;
    }

    /**
     * This method sets the product id.
     *
     * @param productId product ID
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * This method gets the description of an order.
     *
     * @return String description.
     */
    public String getDescription() {
        if (description != null) {
            return description;
        }
        return "Geen beschrijving";
    }

    /**
     * This method gets the average time a product needs to be made.
     *
     * @return int time.
     */
    public int getAverageTime() {
        return averageTime;
    }

    /**
     * This method sets the ammount of products.
     *
     * @param ammount
     */
    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    /**
     * This method sets the name of an dish.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method sets the description of an product.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method sets the average time a product needs to be made.
     *
     * @param averageTime
     */
    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }
}
