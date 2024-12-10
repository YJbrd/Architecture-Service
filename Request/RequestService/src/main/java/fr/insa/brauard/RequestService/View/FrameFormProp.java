package View;
import Controller.Connexion.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameFormProp extends JFrame {

    private JTextArea champDescription;
    private JButton boutonValider;

    public FrameFormProp(String pseudoUtilisateur) {
        setTitle("Formulaire de Proposition d'Aide");
        setSize(400, 300);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        // Champ de description
        champDescription = new JTextArea();
        champDescription.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(champDescription);

        // Bouton de validation
        boutonValider = new JButton("Valider");
        boutonValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String description = champDescription.getText();
                int IDUtilisateur = Controller.Connexion.LogIn.getIDUtilisateur(pseudoUtilisateur);
                System.out.println(IDUtilisateur);
                Model.Tasks.Prop.createNewProp(description, IDUtilisateur);

            }
        });

        // Ajout des composants au panel
        panel.add(scrollPane);
        panel.add(boutonValider);

        add(panel);

        setVisible(true);
    }

}
