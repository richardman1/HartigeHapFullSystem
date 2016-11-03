package nl.infosys.hartigehap.supplySystem.databaseacces;

import nl.infosys.hartigehap.supplySystem.datastore.DataStore;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import nl.infosys.hartigehap.supplySystem.domain.Ingredient;

/**
 *
 * @author Gregor
 */
public class IngredientDao {

    /**
     *
     */
    public IngredientDao() {

    }

    /**
     *
     * @return
     */
    public Map<Integer, Ingredient> getIngredients() {
        //long start, stop;

        Map ingredientList = new LinkedHashMap<>();
        DataStore connection = new DataStore();
        Ingredient ingredient;
        ResultSet result;

        connection.getConnection();

        try {
            //start = System.currentTimeMillis();
            result = connection.executeQuery("SELECT product.Naam, drank.MinVoorraad, drank.Voorraad, drank.fk_ProductID FROM drank LEFT JOIN product ON drank.fk_ProductID = product.ProductID ORDER BY product.Naam ASC");
            while (result.next()) {
                ingredient = new Ingredient(result.getString("Naam"), 0, result.getInt("Voorraad"), result.getInt("MinVoorraad"), result.getInt("fk_ProductID"));
                ingredientList.put(ingredient.getId(), ingredient);
            }
            result = connection.executeQuery("SELECT IngredientID, Naam, MinVoorraad, Voorraad FROM ingredient ORDER BY Naam ASC");
            while (result.next()) {
                ingredient = new Ingredient(result.getString("Naam"), 0, result.getInt("Voorraad"), result.getInt("MinVoorraad"), result.getInt("IngredientID"));
                ingredientList.put(ingredient.getId(), ingredient);
            }
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }

        connection.closeConnection();
        //stop = System.currentTimeMillis();
        //System.out.println("Drink elapsed time: " + (stop - start));
        return ingredientList;
    }

    /**
     *
     * @param ingredient
     */
    public void updateIngredientStock(Ingredient ingredient) {
        DataStore connection = new DataStore();
        connection.getConnection();
        if (ingredient.getId() < 10000) {
            connection.executeUpdate("UPDATE ingredient SET Voorraad = " + ingredient.getStock() + " WHERE IngredientID = " + ingredient.getId());
        } else {
            connection.executeUpdate("UPDATE drank SET Voorraad = " + ingredient.getStock() + " WHERE fk_ProductID = " + ingredient.getId());
        }
        connection.closeConnection();
    }
}
