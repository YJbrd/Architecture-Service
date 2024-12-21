package fr.insa.brauard.RequestService.Model;

import fr.insa.brauard.RequestService.Model.Status;
import fr.insa.brauard.AuthentificationService.Controller.GetUser;
import fr.insa.brauard.AuthentificationService.Model.User;
import fr.insa.brauard.BDDConnexionService.Model.ConnexionDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/task")
public class ModifyStatus {

    @Autowired
    private ConnexionDataBase databaseConnectionService;

    @Autowired
    private GetUser getUserController; // Utilisation de votre contrôleur GetUser pour récupérer l'utilisateur

    @PutMapping("/modify-status/{taskId}")
    public ResponseEntity<String> modifyStatus(@PathVariable int taskId, @RequestParam int userId, @RequestParam Status newStatus) {
        try (Connection con = databaseConnectionService.getConnection()) {

            // Récupérer les informations de l'utilisateur via le contrôleur GetUser
            ResponseEntity<Object> userResponse = getUserController.getUser(userId);
            
            if (userResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            // Extraire l'utilisateur de la réponse
            User user = (User) userResponse.getBody();
            
            if (user == null || !user.getUserType().equals("Validateur")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to modify the task status." + user.getUserType());
            }
            
            // Vérification de l'existence de la tâche
            String checkTaskQuery = "SELECT * FROM Task WHERE ID_Task = ?";
            PreparedStatement checkTaskStmt = con.prepareStatement(checkTaskQuery);
            checkTaskStmt.setInt(1, taskId);
            ResultSet taskResult = checkTaskStmt.executeQuery();

            if (!taskResult.next()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
            }

            // Mise à jour du statut de la tâche
            String updateStatusQuery = "UPDATE Task SET Validation = ? WHERE ID_Task = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateStatusQuery);
            updateStmt.setString(1, newStatus.name());  // On envoie le nom de l'énumération
            updateStmt.setInt(2, taskId);

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                return ResponseEntity.ok("Task status updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating task status.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error modifying task status: " + e.getMessage());
        }
    }
}
