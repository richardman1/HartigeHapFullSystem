package nl.infosys.hartigehap.userSystem.databaseacces;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.infosys.hartigehap.userSystem.datastore.DataStore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sander van Belleghem
 */
public class UserDao {

    private final DataStore dataStore = new DataStore();
    public int ID;
    public String password;
    public String passwordCheck;
    public String functie;

    public UserDao() {

    }

    /**
     * Method for loggin in an user
     *
     * @param username
     * @param password
     * @return boolean true or false
     */
    public boolean login(String username, String password) {

        try {
            dataStore.openConnection();

            ResultSet rs = dataStore.executeSQLSelectStatement("SELECT * FROM medewerker WHERE MedewerkerID = " + username + ";");

            if (rs.next()) {

                String pass = rs.getString("wachtwoord");

                try {
                    MessageDigest md;
                    md = MessageDigest.getInstance("MD5");

                    md.update(password.getBytes());

                    byte byteData[] = md.digest();

                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < byteData.length; i++) {
                        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                    }

                    if (sb.toString().equals(pass)) {

                        functie = rs.getString("functie");

                        dataStore.executeSQLUpdateStatement("UPDATE medewerker SET Status = 'ingelogd' WHERE MedewerkerID = " + username + ";");

                        return true;

                    }
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            return false;

        } catch (SQLException e) {
            System.err.println("Melding: " + e.getMessage());
        }
        dataStore.closeConnection();
        return false;

    }

    /**
     * Method for loggin out an user
     *
     * @param username
     */
    public void logout(String username) {
        dataStore.openConnection();
        dataStore.executeSQLUpdateStatement("UPDATE medewerker SET Status = 'uitgelogd' WHERE MedewerkerID = " + username + ";");
        dataStore.closeConnection();
    }

    /**
     * Method for getting his function
     *
     * @return function of an employee
     */
    public String getFunctie() {
        return functie;
    }

}
