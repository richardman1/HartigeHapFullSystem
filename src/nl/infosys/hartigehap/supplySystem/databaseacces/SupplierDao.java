package nl.infosys.hartigehap.supplySystem.databaseacces;

import nl.infosys.hartigehap.supplySystem.datastore.DataStore;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.infosys.hartigehap.supplySystem.domain.Supplier;

/**
 *
 * @author Gregor
 */
public class SupplierDao {

    /**
     */
    public SupplierDao() {

    }

    /**
     *
     * @return
     */
    public TreeMap<Integer, Supplier> getSuppliers() {
        TreeMap<Integer, Supplier> suppliers = new TreeMap<>();
        DataStore connection = new DataStore();

        connection.getConnection();
        ResultSet result = connection.executeQuery("SELECT "
                + "LeverancierID, LeverancierNaam, Adres "
                + "FROM "
                + "leverancier;");

        try {
            while (result.next()) {
                suppliers.put(result.getInt("LeverancierID"), new Supplier(result.getString("LeverancierNaam"), result.getString("Adres"), result.getInt("LeverancierID")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SupplierDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return suppliers;
    }
}
