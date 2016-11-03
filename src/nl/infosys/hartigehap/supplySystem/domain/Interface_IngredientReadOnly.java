/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.supplySystem.domain;

/**
 *
 * @author Gregor
 */
public interface Interface_IngredientReadOnly {

    /**
     *
     * @return
     */
    public boolean checkUnderMinimumStock();

    /**
     *
     * @return
     */
    public String getName();

    /**
     *
     * @return
     */
    public double getPrice();

    /**
     *
     * @return
     */
    public int getStock();

    /**
     *
     * @return
     */
    public int getMinimumStock();

    /**
     *
     * @return
     */
    public int getId();
}
