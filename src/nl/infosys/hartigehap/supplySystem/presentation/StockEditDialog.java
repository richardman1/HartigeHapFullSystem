/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.supplySystem.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import nl.infosys.hartigehap.supplySystem.businesslogic.IngredientManager;
import nl.infosys.hartigehap.supplySystem.domain.Interface_IngredientReadOnly;

/**
 *
 * @author Gregor
 */
public class StockEditDialog extends javax.swing.JDialog
{

    private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private IngredientManager ingredientManager;
    private Interface_IngredientReadOnly ingredient;

    /**
     * Creates new form AanpasGui
     *
     * @param parent
     * @param ingredientManager
     * @param ingredient
     */
    public StockEditDialog(java.awt.Frame parent, IngredientManager ingredientManager, Interface_IngredientReadOnly ingredient)
    {
        super(parent, true);

        initComponents();
        this.ingredientManager = ingredientManager;
        this.ingredient = ingredient;
        this.textfield_mutatie.getDocument().addDocumentListener(new docListener());
        getProductData();

        this.pack();
        this.setLocation((dim.width / 2) - (this.getSize().width / 2), (dim.height / 2) - (this.getSize().height / 2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textfield_mutatie = new javax.swing.JTextField();
        textfield_nieuweVoorraad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        button_terug = new javax.swing.JButton();
        button_opslaan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);

        jLabel1.setText("Voorraad aanpasscherm");

        jLabel2.setText("Voorraad mutatie");

        textfield_nieuweVoorraad.setEditable(false);
        textfield_nieuweVoorraad.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Nieuwe voorraad");

        button_terug.setText("Terug");
        button_terug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_terugActionPerformed(evt);
            }
        });

        button_opslaan.setText("Opslaan");
        button_opslaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_opslaanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(textfield_mutatie, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1))
                    .addComponent(textfield_nieuweVoorraad))
                .addGap(87, 87, 87))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(button_terug)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_opslaan)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textfield_mutatie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textfield_nieuweVoorraad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_terug)
                    .addComponent(button_opslaan))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_terugActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_button_terugActionPerformed
    {//GEN-HEADEREND:event_button_terugActionPerformed
        this.dispose();
    }//GEN-LAST:event_button_terugActionPerformed

    private void button_opslaanActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_button_opslaanActionPerformed
    {//GEN-HEADEREND:event_button_opslaanActionPerformed
        int inputNumber = 0;

        try
        {
            inputNumber = Integer.parseInt(textfield_nieuweVoorraad.getText());
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(rootPane, "Geen geldig getal ingevoerd.");
            return;
        }
        if (inputNumber < 0)
        {
            JOptionPane.showMessageDialog(rootPane, "De voorraad mag niet negatief zijn.");
        }
        else
        {
            ingredientManager.updateStock(ingredient.getId(), inputNumber);
            ingredientManager.saveIngredient(ingredient.getId());
            this.dispose();
        }
    }//GEN-LAST:event_button_opslaanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_opslaan;
    private javax.swing.JButton button_terug;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField textfield_mutatie;
    private javax.swing.JTextField textfield_nieuweVoorraad;
    // End of variables declaration//GEN-END:variables

    private void getProductData()
    {
        int voorraad = ingredient.getStock();
        
        textfield_nieuweVoorraad.setText(voorraad + "");
        textfield_mutatie.setText("0");
    }

    private void changeVoorraadField()
    {
        int voorraad = ingredient.getStock();
        int invoer = 0;
        try
        {
            invoer = Integer.parseInt(textfield_mutatie.getText());
            textfield_nieuweVoorraad.setBackground(Color.white);
            textfield_nieuweVoorraad.setForeground(Color.black);
            textfield_nieuweVoorraad.setText((voorraad + invoer) + "");
        }
        catch (NumberFormatException e)
        {
            textfield_nieuweVoorraad.setText("Ongeldig getal");
        }
        if ((voorraad + invoer) < 0)
        {
            textfield_nieuweVoorraad.setBackground(new Color(153, 0, 26));
            textfield_nieuweVoorraad.setForeground(new Color(235, 235, 235));
        }
    }

    private class docListener implements DocumentListener
    {

        @Override
        public void insertUpdate(DocumentEvent e)
        {
            changeVoorraadField();
        }

        @Override
        public void removeUpdate(DocumentEvent e)
        {
            changeVoorraadField();
        }

        @Override
        public void changedUpdate(DocumentEvent e)
        {
            changeVoorraadField();
        }
    }
}
