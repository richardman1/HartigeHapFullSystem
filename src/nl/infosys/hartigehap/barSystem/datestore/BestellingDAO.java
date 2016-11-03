/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.barSystem.datestore;

import nl.infosys.hartigehap.barSystem.databaseacces.ConnectionDB;
import nl.infosys.hartigehap.barSystem.domain.Bestelling;
import nl.infosys.hartigehap.barSystem.domain.ImmutableBestelling;
import nl.infosys.hartigehap.barSystem.domain.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @version 1.0
 * @author iVP4C2
 */
public class BestellingDAO {

    /**
     * Method to get a ArrayList containing the bestellingen
     *
     * @param tafelnummer
     * @param status
     * @param soort
     * @return De bestellingen die voldoen aan het tafelnummer, status en soort
     */
    public ArrayList<ImmutableBestelling> getBestellingen(Integer tafelnummer, Bestelling.Status status, Bestelling.Soort soort) {

        // Maak een arraylist aan waar de bestelling worden opgeslagen
        ArrayList<ImmutableBestelling> bestellingen = new ArrayList<ImmutableBestelling>();

        try {
            // Maak een connectie met de database
            ConnectionDB connect = new ConnectionDB();

            String queryTekst = "SELECT * FROM bestellingen_producten";

            String kolom;

            if (status.equals(Bestelling.Status.WILTBETALEN) || status.equals(Bestelling.Status.BETAALD)) {
                kolom = "BetaalStatus";
            } else {
                kolom = "LeverStatus";
            }

            queryTekst += " WHERE " + kolom + " = '" + status.toString() + "'";

            if (tafelnummer != null) {
                queryTekst += " AND tafelNummer = " + tafelnummer;
            }

            queryTekst += getCategorieQueryString(soort);

            //queryTekst += " ORDER BY a.TimeStamp";
            // Haal de tafelnummers op uit de database
            ResultSet rs = connect.executeQuery(queryTekst);

            int previousId = 0;

            HashMap<Integer, ImmutableBestelling> tafelnummers = new HashMap<Integer, ImmutableBestelling>();

            // Loop door alle resultaten
            while (rs.next()) {
                // Maak een nieuw product aan voor de resultaten rij
                Product product = new Product(rs.getString("Naam"), rs.getDouble("Prijs"), rs.getInt("Hoeveelheid"));

                // Controleer of het product bij een bestaande bestelling moet worden toegvoegd of
                // dat er een nieuwe bestelling moet worden aangemaakt.
                if (previousId == rs.getInt("BestellingID")) {
                    // De index van het laatste element van de bestellingArray
                    int index = bestellingen.size() - 1;

                    // Haal de oude bestelling op
                    Bestelling bestelling = (Bestelling) bestellingen.get(index);

                    // Voeg het product toe aan de bestelling
                    bestelling.addProduct(product);

                    // Verander de bestelling in de bestellingArray
                    bestellingen.set(index, bestelling);
                } else if (soort.equals(Bestelling.Soort.VOLLEDIG) && tafelnummers.containsKey(rs.getInt("TafelNummer"))) {
                    // Haal de bestelling op aan de hand van het tafelnummer
                    Bestelling bestelling = (Bestelling) tafelnummers.get(rs.getInt("TafelNummer"));

                    // Haal de index op uit de bestellingen arraylist van deze bestelling
                    int index = bestellingen.lastIndexOf(bestelling);

                    // Voeg het product toe aan de bestelling
                    bestelling.addProduct(product);

                    // Verande de bestelling in de beestellingen arraylist
                    bestellingen.set(index, bestelling);

                    // Verwijder de bestelling uit de tafelnummers hashmap
                    tafelnummers.remove(rs.getInt("TafelNummer"));

                    // Voeg de nieuwe bestelling toe aan de tafelnummers hashmap
                    tafelnummers.put(rs.getInt("TafelNummer"), bestelling);
                } else {
                    // Maak een nieuwe bestelling
                    Bestelling bestelling = new Bestelling(rs.getInt("BestellingID"), rs.getInt("TafelNummer"), soort, status);

                    // Voeg het product toe
                    bestelling.addProduct(product);

                    // Voeg de bestelling toe aan de bestellingArray
                    bestellingen.add(bestelling);

                    // Als het gaat om een volledige bestelling moet de bestelling
                    // ook worden toegevoegd aan de tafelnummers hashmap
                    if (soort.equals(Bestelling.Soort.VOLLEDIG)) {
                        tafelnummers.put(rs.getInt("TafelNummer"), bestelling);
                    }
                }

                // Sla het huidige BestellingID op als previousId
                previousId = rs.getInt("BestellingID");
            }

            // De connectie kan gesloten worden
            connect.close();
        } catch (SQLException e) {
            System.err.println("SQL Foutmelding: " + e.getMessage());
        }

        // Geef de bestellingen terug
        return bestellingen;
    }

    /**
     *
     * @param id
     * @param medewerkersCode
     * @param soort
     * @param status
     * @return if query works, the result is true, otherwise it is false
     */
    public boolean updateBestelregelStatus(int id, int medewerkersCode, Bestelling.Soort soort, Bestelling.Status status) {

        try {
            //maak een connectie met de database
            ConnectionDB connect = new ConnectionDB();

            // Update de status en zet de medewerkerscode
            return connect.executeUpdate("UPDATE bestelregel JOIN product ON bestelregel.fk_ProductID = product.ProductID" + getCategorieQueryString(soort) + " SET LeverStatus = '" + status.toString() + "', fk_MedewerkerID = " + medewerkersCode + " WHERE fk_BestellingID = " + id);

        } catch (SQLException e) {
            System.err.println("SQL Foutmelding: " + e.getMessage());
        }

        return false;
    }

    /**
     *
     * @param tafelnummer
     * @param medewerkersCode
     * @param status
     * @return if query works, the result is true, otherwise it is false
     */
    public boolean updateBestellingStatus(int tafelnummer, int medewerkersCode, Bestelling.Status status) {
        try {
            //maak een connectie met de database
            ConnectionDB connect = new ConnectionDB();

            // Update de status en zet de medewerkerscode
            return connect.executeUpdate("UPDATE bestelling SET BetaalStatus = '" + status.toString() + "', fk_MedewerkerID = " + medewerkersCode + " WHERE tafelnummer = " + tafelnummer + " AND BetaalStatus != '" + status + "'");

        } catch (SQLException e) {
            System.err.println("SQL Foutmelding: " + e.getMessage());
        }

        return false;
    }

    private String getCategorieQueryString(Bestelling.Soort soort) {
        if (!soort.equals(Bestelling.Soort.VOLLEDIG)) {
            // Bepaal welke operator moet worden gebruikt zodat de juiste bestellingen worden opgehaald
            String queryCategorieOperator;

            if (soort.equals(Bestelling.Soort.KEUKEN)) {
                // Operator voor keukenbestelling (!= 'Drankjes')
                queryCategorieOperator = "!=";
            } else {
                // Operator voor barbestelling (= 'Drankjes')
                queryCategorieOperator = "=";
}

            return " AND Categorie " + queryCategorieOperator + " 'Drankjes'";
        }
        
        return "";
    }
}
