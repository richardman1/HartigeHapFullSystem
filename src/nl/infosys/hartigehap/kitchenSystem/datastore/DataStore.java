package nl.infosys.hartigehap.kitchenSystem.datastore;

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
 * @author nlmaudenaerde
 */
public class DataStore {

    Connection con;
    Statement stmt;

    /**
     * Initializes the connection with the database.
     */
    public DataStore() {
        openConnection();
    }

    /**
     * Open a database connection with the server.
     */
    public void openConnection() {
        try {
            Properties properties = new Properties();
            properties.load(mainSystemDao.class.getResourceAsStream("config.properties"));

            // Setup an connection
            con = DriverManager.getConnection(properties.getProperty("database"), properties.getProperty("dbuserC3"), properties.getProperty("dbpasswordC3"));

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
     * Close the connection with the server.
     */
    public void closeConnection() {
        try {
            con.close();
        } catch (Exception e) {
            System.err.println("close connection: Melding1: " + e.getMessage());
        }
    }

    /**
     * This method is used for select queries.
     *
     * @param QueryTekst Contains the query that has to be executed.
     * @return Resultset with queries.
     */
    public ResultSet ExecuteDB(String QueryTekst) {
        ResultSet rs1 = null;
        try {
            rs1 = stmt.executeQuery(QueryTekst);
        } catch (Exception e) {
            System.err.println("select: Melding1: " + e.getMessage());
        }
        return rs1;
    }

    /**
     * This method is used for update and insert queries.
     *
     * @param QueryTekst Contains the query that has to be executed.
     */
    public void UpdateDB(String QueryTekst) {
        try {
            stmt.executeUpdate(QueryTekst);
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error:",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
