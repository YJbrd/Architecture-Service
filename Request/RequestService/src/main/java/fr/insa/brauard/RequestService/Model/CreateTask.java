package fr.insa.brauard.RequestService.Model;

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
public class CreateTask {

    @Autowired
    private ConnexionDataBase databaseConnectionService; // Injection du service de connexion

    @PostMapping(value = "/createTask/{proprioId}/{description}/{dateLimite}")
    public ResponseEntity<String> createTask(@PathVariable int proprioId, 
                                             @PathVariable String description, 
                                             @PathVariable String dateLimite) {

        // Vérification de l'absence de valeurs vides
        if (description == null || description.isEmpty() || dateLimite == null || dateLimite.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Les informations de description et de date limite doivent être fournies et ne peuvent être vides.");
        }

        // Connexion à la base de données et insertion des données
        try (Connection con = databaseConnectionService.getConnection()) {
            System.out.println("Tentative de création de la tâche pour l'utilisateur avec ID: " + proprioId);

            // Requête pour insérer les données dans la table task
            String query = "INSERT INTO Task (ProprioID, Status, Description, DateLimite, Validation) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(query);

            // Définir les valeurs pour la requête préparée
            stm.setInt(1, proprioId); 		// ID du propriétaire
            stm.setString(2, "Non effectuee"); // Statut par défaut
            stm.setString(3, description); 	// Description de la tâche
            stm.setString(4, dateLimite); 	// Date limite
            stm.setString(5, "En attente"); 	// Validation par défaut

            // Exécution de la requête
            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tâche créée avec succès pour l'utilisateur ID: " + proprioId);
                return ResponseEntity.status(HttpStatus.CREATED).body("Tâche créée avec succès.");
            } else {
                System.out.println("Erreur lors de la création de la tâche.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de la tâche.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de la tâche.");
        }
    }
}
