package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ModuleLayer.Controller;

import Controller.Connexion.*;
import Controller.DataUser.*;

public class FrameVulnerable extends JFrame {

    private JLabel labelUtilisateur;
    private JLabel labelTypeUtilisateur;
    private JButton boutonAjouterDemande;
    private DefaultListModel<String> listeDemandesModel;
    private JList<String> listeDemandes;
    
    
    private JButton refresh = new JButton("refresh");
    private JScrollPane scrollPane = new JScrollPane(listeDemandes);



    public FrameVulnerable(String pseudoUtilisateur, String typeUtilisateur) {
        setTitle("Page Principale");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //liste des demandes
        listeDemandesModel = new DefaultListModel<>();

        // En-t�te de la page
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        labelUtilisateur = new JLabel("Pseudo: " + pseudoUtilisateur);
        labelTypeUtilisateur = new JLabel("Type d'Utilisateur: " + typeUtilisateur);
        headerPanel.add(labelUtilisateur);
        headerPanel.add(labelTypeUtilisateur);

        // Bouton pour ajouter des demandes
        boutonAjouterDemande = new JButton("Ajouter une Demande");
        boutonAjouterDemande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {               
                SwingUtilities.invokeLater(() -> new FrameFormTask(pseudoUtilisateur));
            }
        });
        

        // Agencement global avec GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        mainPanel.add(headerPanel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(boutonAjouterDemande, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(refresh);

        add(mainPanel);

        setVisible(true);
        
        int IDUtilisateur = LogIn.getIDUtilisateur(pseudoUtilisateur);
        listeDemandesModel= DataUsers.getTasksList(IDUtilisateur);
        //System.out.println(listeDemandesModel);
        listeDemandes= new JList<>(listeDemandesModel);
        //System.out.println(listeDemandes);
        scrollPane = new JScrollPane(listeDemandes);
        
      // Bouton qui affiche la liste des demandes en cours
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	listeDemandesModel.removeAllElements();
                int IDUtilisateur = LogIn.getIDUtilisateur(pseudoUtilisateur);
                listeDemandesModel= DataUsers.getTasksList(IDUtilisateur);
                System.out.println(listeDemandesModel);
            	listeDemandes.setModel(listeDemandesModel);
            	scrollPane.setViewportView(listeDemandes);
            }
        });
        
        mainPanel.add(scrollPane, gbc);

    }
    
    

}
