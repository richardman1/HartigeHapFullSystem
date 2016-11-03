/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.barSystem.domain;

/**
 * @version 1.0
 * @author iVP4C2
 */
public class Medewerker implements ImmutableMedewerker {

    private String voornaam, achternaam, status;
    private int code;

    /**
     * 
     * @param voornaam
     * @param achternaam
     * @param code
     * @param status 
     */
    public Medewerker(String voornaam, String achternaam, int code, String status) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.code = code;
        this.status = status;
    }

    /**
     * get the first name
     *
     * @return first name
     */
    @Override
    public String getVoornaam() {
        return voornaam;
    }

    /**
     * get the last name
     *
     * @return last name
     */
    @Override
    public String getAchternaam() {
        return achternaam;
    }

    /**
     * get the ID
     *
     * @return return ID
     */
    @Override
    public int getCode() {
        return code;
    }
    
    @Override
    public String getStatus(){
        
        return status;
    }
}
