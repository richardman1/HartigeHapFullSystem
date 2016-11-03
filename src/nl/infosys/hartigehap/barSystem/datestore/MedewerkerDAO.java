/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.barSystem.datestore;

import nl.infosys.hartigehap.barSystem.databaseacces.ConnectionDB;
import nl.infosys.hartigehap.barSystem.domain.ImmutableMedewerker;
import nl.infosys.hartigehap.barSystem.domain.Medewerker;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @version 1.0
 * @author iVP4C2
 */
public class MedewerkerDAO {

    /**
     * Method that gets the Medewerkers
     *
     * @return return Medewerker
     */
    public ArrayList<ImmutableMedewerker> getIngelogdeMedewerkers() {

        // Maak een arraylist aan waar de medwerkers in op kunnen worden geslagen
        ArrayList<ImmutableMedewerker> medewerkers = new ArrayList<ImmutableMedewerker>();

        try {
            // Maak een nieuwe connectie aan
            ConnectionDB connect = new ConnectionDB();

            // Selecteer de ingelogde bediening gebruikers uit de database
            ResultSet rs = connect.executeQuery("SELECT * FROM ingelogde_bediening");

            // Loop door de resultaten
            while (rs.next()) {
                // Voeg de medewerker toe aan de arraylist            
                medewerkers.add(new Medewerker(rs.getString("Voornaam"), rs.getString("Achternaam"), rs.getInt("MedewerkerID"), "Ingelogd"));
            }

            // Sluit de connectie
            connect.close();

        } catch (SQLException e) {
            System.err.println("SQL Foutmelding: " + e.getMessage());
        }

        return medewerkers;
    }

    /**
     *
     * @return return Medewerker
     */
    public ArrayList<ImmutableMedewerker> getAllMedewerkers() {

        try {
            ConnectionDB connect = new ConnectionDB();

            String queryTekst = "SELECT Voornaam, Achternaam, MedewerkerID, Status FROM medewerker ORDER BY Achternaam";

            ResultSet rs = connect.executeQuery(queryTekst);

            ArrayList<ImmutableMedewerker> medewerkers = new ArrayList<ImmutableMedewerker>();

            while (rs.next()) {
                Medewerker medewerker = new Medewerker(rs.getString("Voornaam"), rs.getString("Achternaam"), rs.getInt("MedewerkerID"), rs.getString("Status"));
                medewerkers.add(medewerker);
            }
            connect.close();

            return medewerkers;

        } catch (SQLException e) {
            System.err.println("SQL Foutmelding: " + e.getMessage());
        }

        return null;
    }

    public boolean updateMedewerkerStatus(int code, String status) {

        try {
            // Maak een connectie met de database
            ConnectionDB connect = new ConnectionDB();

            if (code < 1 && status.isEmpty()) {
                return false;
            } else {
                return connect.executeUpdate("UPDATE medewerker SET `Status` = '" + status + "' WHERE `MedewerkerID` = " + code + "");
            }

        } catch (SQLException e) {
            System.err.println("Melding: " + e.getMessage());
        }
        return false;
    }

    /**
     * To check if the medewerkerscode that was inserted actually exists
     *
     * @param medewerkersCode
     * @return boolean if medewekersCode exists or not
     */
    public boolean checkMedewerkersCode(int medewerkersCode) {
        try {
            // Maak een connectie met de database
            ConnectionDB connect = new ConnectionDB();

            // Controleer of de mederwerkersCode in de ingelogde_bediening tabel (view) staat
            ResultSet rs = connect.executeQuery("SELECT MedewerkerID FROM ingelogde_bediening WHERE MedewerkerID = " + medewerkersCode);

            return rs.isBeforeFirst();

        } catch (SQLException e) {
            System.err.println("SQL Foutmelding: " + e.getMessage());
        }

        return false;
    }
}
