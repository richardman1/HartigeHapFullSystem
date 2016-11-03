package nl.infosys.hartigehap.supplySystem.businesslogic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import nl.infosys.hartigehap.supplySystem.databaseacces.IngredientDao;
import nl.infosys.hartigehap.supplySystem.domain.Ingredient;
import nl.infosys.hartigehap.supplySystem.domain.Interface_IngredientReadOnly;

/**
 *
 * @author Gregor
 */
public class IngredientManager {

    Map<Integer, Ingredient> ingredientList;

    public static final int FOOD_INGREDIENT = 0;
    public static final int DRINK_INGREDIENT = 1;

    /**
     *
     */
    public IngredientManager() {
        loadIngredient();
    }

    private void loadIngredient() {
        IngredientDao dao = new IngredientDao();
        ingredientList = new LinkedHashMap<>(dao.getIngredients());
    }

    /**
     * refreshes the internal ingredient list by reloading it from the database.
     */
    public void refreshIngredient() {
        loadIngredient();
    }

    /**
     * Gets all ingredients.
     *
     * @return
     */
    public ArrayList<Interface_IngredientReadOnly> getIngredients() {
        return new ArrayList<Interface_IngredientReadOnly>(ingredientList.values());
    }

    /**
     * Updates an ingredient in the database.
     *
     * @param id
     */
    public void saveIngredient(int id) {
        IngredientDao dao = new IngredientDao();
        dao.updateIngredientStock(ingredientList.get(id));
    }

    public void updateStock(int id, int newValue) {
        ingredientList.get(id).setStock(newValue);
    }

    /**
     * Gets the ingredient belonging to the specified database id.
     *
     * @param id
     * @return The ingredient or null if none was found.
     */
    public Interface_IngredientReadOnly getIngredient(int id) {
        return ingredientList.get(id);
    }

    /**
     * Gets the ingredient with the same name.
     *
     * @param name
     * @return The ingredient or null if none was found.
     */
    public Interface_IngredientReadOnly getIngredient(String name) {
        for (Ingredient ingredient : ingredientList.values()) {
            if (ingredient.getName().equals(name)) {
                return ingredient;
            }
        }
        return null;
    }
}
