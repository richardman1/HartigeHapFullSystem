/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.hartigehap.userSystem.presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.OverlayLayout;
import nl.infosys.hartigehap.userSystem.businesslogic.UserManager;

/**
 *
 * @author Sander van Belleghem
 */
public class UserGui {

    private final UserManager userManager;
    private final JPanel panel = new JPanel();
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel background = new JPanel();

    private final JPanel loginPanel = new JPanel(new GridLayout(3, 2));
    private final JTextField inputFieldUser = new JTextField(10);
    private final JPasswordField inputFieldPass = new JPasswordField(10);
    private final JButton loginButton = new JButton("Login");
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private BufferedImage slide1, slide2 = null;
    private int finalDelay = 10;
    private ArrayList<BufferedImage> images = new ArrayList<>();
    private int idx = 0;

    public UserGui(UserManager userManagerObj, CardLayout cardlayout, JPanel cardpanel) {

        cardLayout = cardlayout;
        cardPanel = cardpanel;

        userManager = userManagerObj;

        initComponents();
        
        // Set focus on inputfield
        inputFieldUser.requestFocusInWindow();
    }

    private void initComponents() {

        LayoutManager overlay = new OverlayLayout(panel);
        panel.setLayout(overlay);

        try {
            slide1 = ImageIO.read(new File(System.getProperty("user.dir") + "\\src\\nl\\infosys\\hartigehap\\userSystem\\presentation\\slide1.png"));
            slide2 = ImageIO.read(new File(System.getProperty("user.dir") + "\\src\\nl\\infosys\\hartigehap\\userSystem\\presentation\\slide2.png"));
        } catch (IOException e) {
            System.out.println("Message: " + e.getMessage());
        }

        //images.add(slide1);
        images.add(slide2);

        background.add(new JLabel(new ImageIcon(images.get(idx))));
        mainPanel.add(background);
/*        panel.revalidate();

        Timer t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (finalDelay <= 1) {

                    System.out.println(idx);
                    
                    if (idx + 1 >= images.size()) {
                        
                        idx = 0;

                        background.add(new JLabel(new ImageIcon(images.get(idx))));

                        mainPanel.add(background);
                        panel.revalidate();

                    } else {

                        idx++;

                        background.add(new JLabel(new ImageIcon(images.get(idx))));

                        mainPanel.add(background);
                        panel.revalidate();

                    }

                    finalDelay = 10;
                }
                finalDelay--;

            }
        });
        t.setRepeats(true);
        t.start();
*/
        loginPanel.add(new JLabel("Gebruikersnaam: "));
        loginPanel.add(inputFieldUser);
        loginPanel.add(new JLabel("Wachtwoord: "));
        loginPanel.add(inputFieldPass);
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);
        loginPanel.setBackground(new Color(211, 211, 211, 246));
        loginPanel.setPreferredSize(new Dimension(300, 100));
        loginPanel.setMaximumSize(loginPanel.getPreferredSize());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        // Add mainPanel to Panel
        panel.add(loginPanel);
        panel.add(mainPanel);

        loginButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
    }

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {

        String username = inputFieldUser.getText();
        String password = inputFieldPass.getText();

        if (username.length() > 0 && password.length() > 0) {
            if (userManager.login(username, password)) {
                inputFieldUser.setText("");
                inputFieldPass.setText("");
                
                cardLayout.show(cardPanel, userManager.getFunctie().toLowerCase());
            } else {
                JOptionPane.showMessageDialog(null, "Gebruikersnaam, wachtwoord combinatie niet gevonden.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Zowel de gebruikersnaam als wachtwoord dient ingevuld te zijn.");
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
