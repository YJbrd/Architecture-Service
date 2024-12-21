package fr.insa.brauard.RequestService.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.brauard.BDDConnexionService.Model.ConnexionDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/task")
public class DeleteTask {

    @Autowired
    private ConnexionDataBase databaseConnectionService;

    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable int id, @RequestParam int idSender) {
        try (Connection con = databaseConnectionService.getConnection()) {
            // Vérifier si l'utilisateur est le propriétaire de la tâche
            String checkTaskQuery = "SELECT ProprioID FROM Task WHERE ID_Task = ?";
            PreparedStatement checkTaskStm = con.prepareStatement(checkTaskQuery);
            checkTaskStm.setInt(1, id);
            ResultSet taskResult = checkTaskStm.executeQuery();

            if (taskResult.next()) {
                int proprioId = taskResult.getInt("ProprioID");

                if (proprioId == idSender) {
                    // Supprimer la tâche
                    String deleteQuery = "DELETE FROM Task WHERE ID_Task = ?";
                    PreparedStatement deleteStm = con.prepareStatement(deleteQuery);
                    deleteStm.setInt(1, id);
                    deleteStm.executeUpdate();
                    return "Task deleted successfully.";
                } else {
                    return "You can only delete tasks you have created.";
                }
            } else {
                return "Task not found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting task: " + e.getMessage();
        }
    }
}

