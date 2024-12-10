package View;
import Controller.Connexion.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameFormTask extends JFrame {

    private JTextArea champDescription;
    private JTextField champDateLimite;
    private JButton boutonValider;

    public FrameFormTask(String pseudoUtilisateur) {
        setTitle("Formulaire de Demande d'Aide");
        setSize(400, 300);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        // Champ de description
        champDescription = new JTextArea();
        champDescription.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(champDescription);

        // Champ de date
        champDateLimite = new JTextField();

        // Bouton de validation
        boutonValider = new JButton("Valider");
        boutonValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String description = champDescription.getText();
                String dateLimite = champDateLimite.getText();
                int IDUtilisateur = Controller.Connexion.LogIn.getIDUtilisateur(pseudoUtilisateur);
                System.out.println(IDUtilisateur);
                Model.Tasks.Task.createNewTask(description, IDUtilisateur, dateLimite);

            }
        });

        // Ajout des composants au panel
        panel.add(scrollPane);
        panel.add(champDateLimite);
        panel.add(boutonValider);

        add(panel);

        setVisible(true);
    }

}
