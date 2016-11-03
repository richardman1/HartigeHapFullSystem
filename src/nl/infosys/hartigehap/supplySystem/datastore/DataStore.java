package nl.infosys.hartigehap.supplySystem.datastore;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JOptionPane;
import nl.infosys.hartigehap.mainSystem.datebaseacces.mainSystemDao;

/**
 *
 * @author Gregor
 */
public class DataStore {

    Connection con; //Interface java.sql package
    Statement stmt; //Interface java.sql package

    ResultSet rs; //Interface java.sql package

    /**
     *
     */
    public DataStore() {

        stmt = null;
    }

    /**
     *
     */
    public void getConnection() {
        try {
            Properties properties = new Properties();
            properties.load(mainSystemDao.class.getResourceAsStream("config.properties"));

            // Setup an connection
            con = DriverManager.getConnection(properties.getProperty("database"), properties.getProperty("dbuserC4"), properties.getProperty("dbpasswordC4"));

            // stmt initialiseren 
            stmt = con.createStatement();

//            if (!con.isClosed()) {
//                System.out.println("Successfully connected to " + "MySQL server using TCP/IP...");
//            }
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error:",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     */
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error:",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param query
     */
    public void executeUpdate(String query) {
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    /**
     *
     * @param query
     * @return
     */
    public int executeUpdateWithLastKey(String query) {
        int lastKey = 0;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet keyResult = stmt.getGeneratedKeys();
            keyResult.next();
            lastKey = keyResult.getInt(1);
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }

        return lastKey;
    }

    /**
     *
     * @param query
     * @return
     */
    public ResultSet executeQuery(String query) {
        try {
            stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }
}
