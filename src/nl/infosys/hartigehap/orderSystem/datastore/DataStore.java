/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.orderSystem.datastore;

/**
 *
 * @author Alex de Vogel
 */
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import javax.swing.JOptionPane;
import nl.infosys.hartigehap.mainSystem.datebaseacces.mainSystemDao;

public class DataStore {

    Connection con; //Interface java.sql package
    Statement stmt; //Interface java.sql package
    ResultSet rs; //Interface java.sql package

    private int insertedId;

    /**
     * Creates DataStore
     */
    public DataStore() {

        stmt = null;
    }

    /**
     * Method to open an database connection
     */
    public void openConnection() {
        try {
            Properties properties = new Properties();
            properties.load(mainSystemDao.class.getResourceAsStream("config.properties"));

            // Setup an connection
            con = DriverManager.getConnection(properties.getProperty("database"), properties.getProperty("dbuserC1"), properties.getProperty("dbpasswordC1"));

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
     * Method to execute an SQL insert query
     *
     * @param query
     * @return last inserted id
     */
    public int executeSQLInsertStatement(String query) {
        try {
            stmt.execute(query, Statement.RETURN_GENERATED_KEYS);

            rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                insertedId = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Melding: " + e.getMessage());
        }

        return insertedId;
    }

    /**
     * Method to execute an SQL select query
     *
     * @param query
     * @return ResultSet
     */
    public ResultSet executeSQLSelectStatement(String query) {
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Melding: " + e.getMessage());
        }
        return rs;
    }

    /**
     * Method to execute an SQL update query
     *
     * @param query
     */
    public void executeSQLUpdateStatement(String query) {
        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Melding: " + e.getMessage());
        }
    }

    /**
     * Method to close the database connection
     */
    public void closeConnection() {
        try {
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error:",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.toString(), "Error:",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
