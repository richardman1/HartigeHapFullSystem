package nl.infosys.hartigehap.supplySystem.domain;

/**
 *
 * @author Gregor
 */
public class Ingredient implements Interface_IngredientReadOnly {

    private String name;
    private double price;
    private int stock, minimumStock, id;

    /**
     *
     * @param name
     * @param price
     * @param stock
     * @param minimumStock
     * @param id
     */
    public Ingredient(String name, double price, int stock, int minimumStock, int id) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.minimumStock = minimumStock;
        this.id = id;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean checkUnderMinimumStock() {
        return stock < minimumStock;
    }

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     *
     * @return
     */
    @Override
    public int getStock() {
        return stock;
    }

    /**
     *
     * @return
     */
    @Override
    public int getMinimumStock() {
        return minimumStock;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @param minimumStock
     */
    public void setMinimumStock(int minimumStock) {
        this.minimumStock = minimumStock;
    }

    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

}
