/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.barSystem.presentation;

import nl.infosys.hartigehap.barSystem.domain.Bestelling;
import nl.infosys.hartigehap.barSystem.domain.ImmutableBestelling;
import nl.infosys.hartigehap.barSystem.domain.ImmutableProduct;
import nl.infosys.hartigehap.barSystem.businesslogic.BedieningManager;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author iVP4C2
 */
public class OpenBestellingenTabel extends JPanel {

    private BedieningManager manager;
    private Bestelling.Soort soort;
    private JComboBox box;

    public OpenBestellingenTabel(BedieningManager manager, Bestelling.Soort soort, JComboBox box) {
        this.manager = manager;
        this.soort = soort;
        this.box = box;

        make();
    }

    public void make() {

        setLayout(new GridLayout(0, 4));

        add(new JLabel("Tafelnummer:"));
        add(new JLabel("Product:"));
        add(new JLabel("Aantal:"));
        add(new JLabel("Bevestig:"));

        ArrayList<ImmutableBestelling> bestellingen = manager.getOpenBestellingen(soort);

        for (final ImmutableBestelling bestelling : bestellingen) {

            if (!bestelling.getProducten().isEmpty()) {

                int i = 1;

                for (ImmutableProduct product : bestelling.getProducten()) {

                    add(new JLabel(Integer.toString(bestelling.getTafelNummer())));
                    add(new JLabel(product.getNaam()));
                    add(new JLabel(Integer.toString(product.getAantal())));

                    if (i == bestelling.getAantalProducten()) {
                        
                        JButton button = new JButton("Bevestig");

                        button.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    if (manager.bestellingUitserveren((int) box.getSelectedItem(), bestelling.getId())) {
                                        removeAll();
                                        make();
                                        validate();
                                        repaint();
                                    } else {
                                        throw new NumberFormatException();
                                    }

                                } catch (NumberFormatException n) {
                                    JOptionPane.showMessageDialog(null, "Foutieve medewerkerscode", "Fout", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        });

                        add(button);
                    } else {
                        add(new JLabel(""));
                        add(new JLabel(""));
                    }

                    i++;
                }
            }
        }
    }
}
