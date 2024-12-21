package fr.insa.brauard.AuthentificationService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.brauard.BDDConnexionService.Model.ConnexionDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@RestController
//@RequestMapping("/auth")
public class Register {

    @Autowired
    private ConnexionDataBase databaseConnectionService; // Injection du service de connexion

    @PostMapping(value = "/register/{pseudo}/{password}/{role}")
    public ResponseEntity<String> createUser(@PathVariable String pseudo, 
                                             @PathVariable String password,
                                             @PathVariable String role) {

        // Vérification de l'absence de valeurs vides
        if (pseudo == null || password == null || pseudo.isEmpty() || password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Les informations (nom, prénom, mot de passe) doivent être fournies et ne peuvent être vides.");
        }

        // Validation du rôle (choix parmi "Benevole", "Vulnerable", "Validateur")
        if (!role.equals("Benevole") && !role.equals("Vulnerable") && !role.equals("Validateur")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Le rôle doit être l'un des suivants: Benevole, Vulnerable, Validateur.");
        }

        // Connexion à la base de données et insertion des données
        try (Connection con = databaseConnectionService.getConnection()) {
            System.out.println("Tentative de création de l'utilisateur: " + pseudo);

            // Requête pour insérer les données dans la table user
            String query = "INSERT INTO User (UserPseudo, UserPassword, UserReview, UserType) VALUES (?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(query);

            // Définir les valeurs pour la requête préparée
            stm.setString(1, pseudo); 	// Pseudo
            stm.setString(2, password); // Mot de passe
            stm.setString(3, null); 	// Avis (null pour l'instant)
            stm.setString(4, role); 	// Type d'utilisateur (rôle choisi)

            // Exécution de la requête
            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilisateur créé avec succès: " + pseudo + " " + " avec rôle " + role);
                return ResponseEntity.status(HttpStatus.CREATED).body("Utilisateur créé avec succès avec le rôle " + role + ".");
            } else {
                System.out.println("Erreur lors de la création de l'utilisateur.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de l'utilisateur.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de l'utilisateur.");
        }
    }
}
