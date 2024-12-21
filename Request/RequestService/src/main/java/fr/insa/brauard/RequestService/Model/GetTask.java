package fr.insa.brauard.RequestService.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import fr.insa.brauard.BDDConnexionService.Model.ConnexionDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class GetTask {

    @Autowired
    private ConnexionDataBase databaseConnectionService;

    // Endpoint pour récupérer une tâche par ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getTask(@PathVariable int id) {
        try (Connection con = databaseConnectionService.getConnection()) {
            String query = "SELECT * FROM Task WHERE ID_Task = ?";
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                // Créez un objet JSON-like sous forme de Map
                Map<String, Object> task = new HashMap<>();
                task.put("idTask", rs.getInt("ID_Task"));
                task.put("proprioId", rs.getInt("ProprioID"));
                task.put("status", rs.getString("Status"));
                task.put("description", rs.getString("Description"));
                task.put("dateLimite", rs.getString("DateLimite"));
                task.put("validation", rs.getString("Validation"));

                return ResponseEntity.ok(task); // Retourne un seul objet lisible
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving task: " + e.getMessage());
        }
    }

    // Endpoint pour récupérer toutes les tâches
    @GetMapping("/all")
    public ResponseEntity<Object> getAllTasks() {
        ArrayList<Map<String, Object>> taskList = new ArrayList<>();
        try (Connection con = databaseConnectionService.getConnection()) {
            String query = "SELECT * FROM Task";
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                // Créez un objet JSON-like sous forme de Map pour chaque tâche
                Map<String, Object> task = new HashMap<>();
                task.put("idTask", rs.getInt("ID_Task"));
                task.put("proprioId", rs.getInt("ProprioID"));
                task.put("status", rs.getString("Status"));
                task.put("description", rs.getString("Description"));
                task.put("dateLimite", rs.getString("DateLimite"));
                task.put("validation", rs.getString("Validation"));

                taskList.add(task); // Ajoute l'objet à la liste
            }
            return ResponseEntity.ok(taskList); // Retourne une liste d'objets JSON-like
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving tasks: " + e.getMessage());
        }
    }
}
