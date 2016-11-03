package nl.infosys.hartigehap.supplySystem.domain;

/**
 *
 * @author Gregor
 */
public class PurchaseOrderLine {
    //Constants

    //Parameters
    Interface_IngredientReadOnly ingredient;
    int amount;
    double price;

    //Constructor
    public PurchaseOrderLine(Interface_IngredientReadOnly ingredient, int amount, double price) {
        this.ingredient = ingredient;
        this.amount = amount;
        this.price = price;
    }

    //Methods
    //Getters & setters
    public Interface_IngredientReadOnly getIngredient() {
        return ingredient;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public double getLineTotal() {
        return amount * price;
    }
}
