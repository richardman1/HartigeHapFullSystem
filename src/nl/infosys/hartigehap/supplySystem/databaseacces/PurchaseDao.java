package nl.infosys.hartigehap.supplySystem.databaseacces;

import nl.infosys.hartigehap.supplySystem.datastore.DataStore;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.infosys.hartigehap.supplySystem.businesslogic.IngredientManager;
import nl.infosys.hartigehap.supplySystem.businesslogic.PurchaseManager.Status;
import nl.infosys.hartigehap.supplySystem.domain.PurchaseOrder;
import nl.infosys.hartigehap.supplySystem.domain.PurchaseOrderLine;
import nl.infosys.hartigehap.supplySystem.domain.Supplier;

/**
 *
 * @author Gregor
 */
public class PurchaseDao {

    int queries = 0;

    /**
     *
     */
    public PurchaseDao() {
    }

    /**
     *
     * @param supplierList
     * @param ingredientManager
     * @return
     */
    public Map<Integer, PurchaseOrder> getPurchaseOrders(Map<Integer, Supplier> supplierList, IngredientManager ingredientManager) {
        //long start, stop;
        //start = System.currentTimeMillis();
        DataStore connection = new DataStore();
        Map<Integer, PurchaseOrder> orders = new LinkedHashMap();
        ResultSet result;
        connection.getConnection();

        result = connection.executeQuery("SELECT "
                + "FactuurID, Factuurstatus, UNIX_TIMESTAMP(Datum) as Datum, fk_LeverancierID "
                + "FROM "
                + "factuur;");

        try {
            while (result.next()) {
                Status status = null;
                switch (result.getString("Factuurstatus")) {
                    case "Offerte":
                        status = Status.OPEN;
                        break;
                    case "Afgewezen":
                        status = Status.CANCELED;
                        break;
                    case "Besteld":
                        status = Status.ORDERED;
                        break;
                    case "Geleverd":
                        status = Status.DELIVERD;
                        break;
                }

                PurchaseOrder order = new PurchaseOrder(result.getInt("FactuurID"), result.getLong("Datum"), status);

                order.setSupplier(supplierList.get(result.getInt("fk_LeverancierID")));
                getPurchaseOrderLines(order, ingredientManager, connection);
                orders.put(order.getId(), order);
            }
        } catch (SQLException ex) {
            throw new Error(ex.getMessage());
        }

        connection.closeConnection();

        //stop = System.currentTimeMillis();
        //System.out.println("Purchase elapsed time: " + (stop - start));
        return orders;
    }

    /**
     *
     * @param order
     * @param ingredientManager
     * @return
     */
    private void getPurchaseOrderLines(PurchaseOrder order, IngredientManager ingredientManager, DataStore connection) {
        ResultSet result;

        //Alle drankregels ophalen.
        result = connection.executeQuery("SELECT  "
                + "`Hoeveelheid`, "
                + "`Inkoopprijs`, "
                + "COALESCE(`fk_ProductID`,`fk_IngredientID`) AS ID "
                + "FROM "
                + "`factuurregel` "
                + "WHERE "
                + "`fk_FactuurID` = " + order.getId() + ";");

        try {
            while (result.next()) {
                order.addOrderLine(ingredientManager.getIngredient(result.getInt("ID")), result.getInt("Hoeveelheid"), result.getDouble("Inkoopprijs"));
            }
        } catch (SQLException ex) {
            throw new Error(ex.getMessage());
        }
    }

    /**
     *
     * @param order
     */
    public void saveOrder(PurchaseOrder order) {
        DataStore connection = new DataStore();
        connection.getConnection();
        ResultSet result;

        result = connection.executeQuery("SELECT FactuurID FROM Factuur WHERE FactuurID = " + order.getId() + ";");
        try {
            if (result.isBeforeFirst()) {
                connection.executeUpdate("UPDATE Factuur SET Factuurstatus = '" + order.getStatus().getPresentationString() + "' WHERE FactuurID = " + order.getId() + ";");
                if (order.isPendingUpdate()) {
                    connection.executeUpdate("DELETE FROM Factuurregel WHERE fk_FactuurID = " + order.getId() + ";");
                    saveOrderLines(order.getId(), order.getOrderlines(), connection);
                }
            } else {
                connection.executeUpdate("INSERT INTO Factuur(FactuurID, Datum, Factuurstatus, fk_LeverancierID) VALUES (" + order.getId() + ", FROM_UNIXTIME(" + order.getTimestamp() + "), '" + order.getStatus().getPresentationString() + "', " + order.getSupplier().getId() + ");");
                saveOrderLines(order.getId(), order.getOrderlines(), connection);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection.closeConnection();
    }

    private void saveOrderLines(int orderId, List<PurchaseOrderLine> orderLines, DataStore connection) {
        for (PurchaseOrderLine orderline : orderLines) {
            if (orderline.getIngredient().getId() < 10000) {
                //System.out.println("INSERT INTO Factuurregel (fk_FactuurID, Hoeveelheid, Inkoopprijs, fk_IngredientID) VALUES (" + order.getId() + ", " + orderline.getAmount() + ", " + orderline.getPrice() + ", " + orderline.getIngredient().getId() + ")");
                connection.executeUpdate("INSERT INTO Factuurregel (fk_FactuurID, Hoeveelheid, Inkoopprijs, fk_IngredientID) VALUES (" + orderId + ", " + orderline.getAmount() + ", " + orderline.getPrice() + ", " + orderline.getIngredient().getId() + ")");
            } else {
                connection.executeUpdate("INSERT INTO Factuurregel (fk_FactuurID, Hoeveelheid, Inkoopprijs, fk_ProductID) VALUES (" + orderId + ", " + orderline.getAmount() + ", " + orderline.getPrice() + ", " + orderline.getIngredient().getId() + ")");
            }
        }
    }
}
