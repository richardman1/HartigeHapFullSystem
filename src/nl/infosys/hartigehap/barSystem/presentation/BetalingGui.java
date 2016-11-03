/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.barSystem.presentation;

import nl.infosys.hartigehap.barSystem.domain.Bestelling;
import nl.infosys.hartigehap.barSystem.domain.ImmutableBestelling;
import nl.infosys.hartigehap.barSystem.businesslogic.BedieningManager;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Olivier
 */
public class BetalingGui extends JPanel {

    private BedieningManager manager;
    private JComboBox box;
    private JCheckBox bon;

    public BetalingGui(BedieningManager manager, JComboBox box) {
        this.manager = manager;
        this.box = box;

        makeFrame();
    }

    public void makeFrame() {

        //Frame en container
        setLayout(new GridLayout(0, 4));
        add(new JLabel("Tafelnummer:"));
        add(new JLabel("Totaalprijs:"));
        add(new JLabel("Bon?"));
        add(new JLabel("Bevestig:"));

        //Zet de tafelnummers in een arraylist
        ArrayList<ImmutableBestelling> Bestellingen = manager.getBestellingen(Bestelling.Status.WILTBETALEN, Bestelling.Soort.VOLLEDIG);

        //Zuidpaneel
        for (final ImmutableBestelling bestelling : Bestellingen) {

            add(new JLabel(Integer.toString(bestelling.getTafelNummer())));
            add(new JLabel(bestelling.getTotaalPrijsFormat()));

            bon = new JCheckBox();
            add(bon);

            JButton button = new JButton("ok");
            add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                    manager.updateBestellingStatus(bestelling.getTafelNummer(), (int) box.getSelectedItem(), Bestelling.Status.BETAALD);
                    
                    if (bon.isSelected()) {
                        new BonGui(bestelling);
                    }
                }
            });
        }
    }
}
