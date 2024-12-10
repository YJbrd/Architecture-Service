package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.Connexion.*;
import Controller.DataUser.*;
import Model.Tasks.*;

public class FrameBenevole extends JFrame {

    private JLabel labelUtilisateur;
    private JLabel labelTypeUtilisateur;
    private DefaultListModel<String> listeDemandesModel;
    private JList<String> listeDemandes;

    private JButton refresh = new JButton("Refresh");
    private JScrollPane scrollPane = new JScrollPane(listeDemandes);

    private JTextField userInputField;
    private JButton validationButton;   
    private JComboBox<String> dropdownMenu;
    private JButton proposeAideButton;


    public FrameBenevole(String pseudoUtilisateur, String typeUtilisateur) {
        setTitle("Page Principale");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Liste des demandes
        listeDemandesModel = new DefaultListModel<>();

        // En-tête de la page
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        labelUtilisateur = new JLabel("Pseudo: " + pseudoUtilisateur);
        labelTypeUtilisateur = new JLabel("Type d'Utilisateur: " + typeUtilisateur);
        headerPanel.add(labelUtilisateur);
        headerPanel.add(labelTypeUtilisateur);

        // Ajout des entrées utilisateur
        JPanel userInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userInputField = new JTextField(10);
        validationButton = new JButton("Valider");
        dropdownMenu = new JComboBox<>(new String[]{"Effectuée", "En cours", "Non effectuable"});
        proposeAideButton = new JButton("Proposer son aide");

        userInputPanel.add(new JLabel("Entrée Utilisateur:"));
        userInputPanel.add(userInputField);
        userInputPanel.add(dropdownMenu);
        userInputPanel.add(validationButton);
        userInputPanel.add(proposeAideButton);

        
        // Agencement global avec GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        mainPanel.add(headerPanel, gbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(userInputPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(refresh);


        add(mainPanel);

        setVisible(true);

        int IDUtilisateur = LogIn.getIDUtilisateur(pseudoUtilisateur);
        listeDemandesModel = DataUsers.getTasksList(IDUtilisateur);
        listeDemandes = new JList<>(listeDemandesModel);
        scrollPane = new JScrollPane(listeDemandes);

        // Bouton qui affiche la liste des demandes en cours
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listeDemandesModel.removeAllElements();
                listeDemandesModel = DataUsers.getTasksListAll();
                listeDemandes.setModel(listeDemandesModel);
                scrollPane.setViewportView(listeDemandes);
            }
        });
        
        validationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = userInputField.getText();
                int numTask = Integer.parseInt(userInput);
                
                String selectedOption = (String) dropdownMenu.getSelectedItem();
                String StatusTask = selectedOption;
                Task.updateStatus(numTask, StatusTask);
            }
        });

        proposeAideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new FrameFormProp(pseudoUtilisateur));

            }
        });
        
        mainPanel.add(scrollPane, gbc);
    }
}
