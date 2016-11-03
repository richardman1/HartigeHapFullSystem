/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.barSystem.businesslogic;

import nl.infosys.hartigehap.barSystem.domain.Bestelling;
import nl.infosys.hartigehap.barSystem.domain.Bon;
import nl.infosys.hartigehap.barSystem.domain.ImmutableBestelling;
import nl.infosys.hartigehap.barSystem.domain.ImmutableMedewerker;
import nl.infosys.hartigehap.barSystem.databaseacces.BestellingDao;
import nl.infosys.hartigehap.barSystem.databaseacces.MedewerkerDao;
import java.util.ArrayList;

/**
 * @version 1.0
 * @author iVP4C2
 */
public class BedieningManagerImpl implements BedieningManager {

    /**
     * Method that gets all the open orders
     *
     * @param soort
     * @return De bestellingen van een bepaalde soort die open staan
     */
    @Override
    public ArrayList<ImmutableBestelling> getOpenBestellingen(Bestelling.Soort soort) {

        if (soort.equals(Bestelling.Soort.BAR)) {
            // Voor de drankjes is de status besteld, omdat deze niet op gereed worden gezet
            return getBestellingen(Bestelling.Status.BESTELD, soort);
        }

        return getBestellingen(Bestelling.Status.GEREED, soort);
    }

    /**
     * Method that gets all the orders with a certain status and soort
     *
     * @param status
     * @param soort
     * @return De bestellingen die voldoen aan een bepaalde status en soort
     */
    @Override
    public ArrayList<ImmutableBestelling> getBestellingen(Bestelling.Status status, Bestelling.Soort soort) {
        return getBestellingen(null, status, soort);
    }

    /**
     * Method that gets all the orders with a certain tafelnummer and status
     *
     * @param tafelnummer
     * @param status
     * @return De bestellingen die voldoen aan een bepaald tafelnummer en status
     */
    @Override
    public ArrayList<ImmutableBestelling> getBestellingen(Integer tafelnummer, Bestelling.Status status) {
        return getBestellingen(tafelnummer, status, Bestelling.Soort.VOLLEDIG);
    }

    /**
     * Method that gets all the orders with a certain tafelnummer and soort and
     * status
     *
     * @param tafelnummer
     * @param status
     * @param soort
     * @return De bestellingen die voldoen aan het tafelnummer, status en soort
     */
    @Override
    public ArrayList<ImmutableBestelling> getBestellingen(Integer tafelnummer, Bestelling.Status status, Bestelling.Soort soort) {
        BestellingDao bestellingDAO = new BestellingDao();

        return bestellingDAO.getBestellingen(tafelnummer, status, soort);
    }

    /**
     * Method calling the updateMethod to update the order containing a certain
     * bestellingsID with a medewerkersCode and status
     *
     * @param medewerkersCode
     * @param bestellingsID
     * @param soort
     */
    @Override
    public boolean bestellingUitserveren(int medewerkersCode, int bestellingsID, Bestelling.Soort soort) {
        MedewerkerDao medewerkerDAO = new MedewerkerDao();

        if (medewerkerDAO.checkMedewerkersCode(medewerkersCode)) {
            BestellingDao bestellingDAO = new BestellingDao();

            return bestellingDAO.updateBestelregelStatus(bestellingsID, medewerkersCode, soort, Bestelling.Status.GELEVERD);
        }

        return false;
    }

    /**
     * Getting a list of orders that want to pay
     *
     * @return De bestellingen die de klanten willen betalen.
     */
    @Override
    public ArrayList<ImmutableBestelling> getBestellingenWiltBetalen() {
        return getBestellingen(Bestelling.Status.WILTBETALEN, Bestelling.Soort.VOLLEDIG);
    }

    /**
     * Method updating a order status using a tafelnummer, medewerkersCode and
     * status
     *
     * @param tafelnummer
     * @param medewerkersCode
     * @param status
     * @return true or false
     */
    @Override
    public boolean updateBestellingStatus(int tafelnummer, int medewerkersCode, Bestelling.Status status) {
        MedewerkerDao medewerkerDAO = new MedewerkerDao();

        if (medewerkerDAO.checkMedewerkersCode(medewerkersCode)) {
            BestellingDao bestellingDAO = new BestellingDao();

            return bestellingDAO.updateBestellingStatus(tafelnummer, medewerkersCode, status);
        }

        return false;
    }

    /**
     * Method to get the logged in Medewerkers
     *
     * @return the Medewerkers
     */
    @Override
    public ArrayList<ImmutableMedewerker> getIngelogdeMedewerkers() {
        MedewerkerDao medewerkerDAO = new MedewerkerDao();

        return medewerkerDAO.getIngelogdeMedewerkers();
    }

    @Override
    public ArrayList<ImmutableMedewerker> getAllMedewerkers() {
        MedewerkerDao medewerkerDAO = new MedewerkerDao();

        return medewerkerDAO.getAllMedewerkers();
    }

    @Override
    public boolean updateMedewerkerStatus(int code, String status) {
        MedewerkerDao medewerkerDAO = new MedewerkerDao();

        return medewerkerDAO.updateMedewerkerStatus(code, status);
    }

    /**
     * Method to get the medewerkersCodes
     *
     * @return the codes or nothing
     */
    @Override
    public Integer[] getMedewerkersCodes(ArrayList<ImmutableMedewerker> medewerkers) {

        if (medewerkers.size() > 0) {
            Integer[] codes = new Integer[medewerkers.size()];

            int i = 0;

            for (ImmutableMedewerker medewerker : medewerkers) {
                codes[i] = medewerker.getCode();

                i++;
            }

            return codes;
        }

        return new Integer[0];
    }

    /**
     * Method to print the bon
     *
     * @param bestelling
     */
    @Override
    public void printBon(ImmutableBestelling bestelling) {
        new Bon(bestelling);
    }
}
