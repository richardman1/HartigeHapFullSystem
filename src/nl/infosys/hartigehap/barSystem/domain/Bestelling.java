/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.barSystem.domain;

import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author IVP3C2
 */
public class Bestelling implements ImmutableBestelling {

    /**
     * creating a enum
     */
    public enum Status {

        /**
         * Besteld status
         */
        BESTELD,
        /**
         * Gereed status
         */
        GEREED,
        /**
         * Geleverd status
         */
        GELEVERD,
        /**
         * Betaald status
         */
        BETAALD,
        /**
         * Wilt betalen status
         */
        WILTBETALEN
    };

    /**
     * Creating a soort enum
     */
    public enum Soort {

        /**
         * Keuken soort
         */
        KEUKEN,
        /**
         * Bar soort
         */
        BAR,
        /**
         * Volledig soort = Bar + Keuken
         */
        VOLLEDIG
    };

    private int tafelNummer, id;
    private double totaalPrijs;
    private ArrayList<Product> producten;
    private Status status;
    private Soort soort;

    /**
     * Maak een nieuwe bestelling aan
     *
     * @param id
     * @param tafelNummer
     * @param soort
     * @param status
     */
    public Bestelling(int id, int tafelNummer, Soort soort, Status status) {
        this.tafelNummer = tafelNummer;
        this.totaalPrijs = 0;
        this.producten = new ArrayList<Product>();
        this.soort = soort;
        this.id = id;
        this.status = status;
    }

    /**
     * Haal de totaalPrijs op van de bestelling
     *
     * @return De totaalprijs van de bestelling
     */
    @Override
    public double getTotaalPrijs() {
        return totaalPrijs;
    }

    /**
     * The the price without BTW
     *
     * @return price without BTW
     */
    @Override
    public double getBtwBedrag() {
        return totaalPrijs - (totaalPrijs / 1.21);
    }

    /**
     * Haal het tafelNummer op van de bestelling
     *
     * @return Het tafelnummer van de bestelling
     */
    @Override
    public int getTafelNummer() {
        return tafelNummer;
    }

    /**
     * Haal de status op van de bestelling
     *
     * @return De status van de bestelling (In Verwerking, gereed, geleverd,
     * betaald)
     */
    @Override
    public Status getStatus() {
        return status;
    }

    /**
     * Voeg een product toe aan de bestelling
     *
     * @param product
     */
    public void addProduct(Product product) {
        producten.add(product);

        totaalPrijs += product.getTotaalPrijs();
    }

    /**
     * Get the products
     *
     * @return De producten uit de bestelling
     */
    @Override
    public ArrayList<Product> getProducten() {
        return producten;
    }

    /**
     * The the amount of products
     *
     * @return Het aantal producten in de bestelling
     */
    @Override
    public int getAantalProducten() {
        return producten.size();
    }

    /**
     * Get the soort products
     *
     * @return De soort bestelling (keuken of bar)
     */
    @Override
    public Soort getSoort() {
        return soort;
    }

    /**
     * Get the ID of products
     *
     * @return Het id van de bestelling
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * The format for money for the price
     *
     * @param prijs
     * @return the money format
     */
    private String formatGeld(double prijs) {
        return String.valueOf(java.text.NumberFormat.getCurrencyInstance(Locale.ITALY).format(prijs));
    }

    /**
     * get the total price in the money format
     *
     * @return price in money format
     */
    @Override
    public String getTotaalPrijsFormat() {
        return formatGeld(totaalPrijs);
    }

    /**
     * Get the price before BTW in money format
     *
     * @return price in money format
     */
    @Override
    public String getBtwBedragFormat() {
        return formatGeld(getBtwBedrag());
    }

    /**
     * Get the amount of BTW
     *
     * @return amount of BTW in moneyformat
     */
    @Override
    public String getPrijsExclBtwFormat() {
        return formatGeld(totaalPrijs - getBtwBedrag());
    }
}
