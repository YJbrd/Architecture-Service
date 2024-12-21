package fr.insa.brauard.AuthentificationService.Controller;

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
@RequestMapping("/user")
public class DeleteUser {

    @Autowired
    private ConnexionDataBase databaseConnectionService;

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, @RequestParam int idSender) {
        // Vérifier que l'utilisateur qui demande la suppression est le propriétaire du compte
        if (id != idSender) {
            return "You can only delete your own account.";
        }

        try (Connection con = databaseConnectionService.getConnection()) {
            // Vérifier si l'utilisateur à supprimer existe
            String checkUserQuery = "SELECT UserID FROM User WHERE UserID = ?";
            PreparedStatement checkUserStm = con.prepareStatement(checkUserQuery);
            checkUserStm.setInt(1, id);
            ResultSet userResult = checkUserStm.executeQuery();

            if (userResult.next()) {
                // Supprimer l'utilisateur
                String deleteQuery = "DELETE FROM User WHERE UserID = ?";
                PreparedStatement deleteStm = con.prepareStatement(deleteQuery);
                deleteStm.setInt(1, id);
                deleteStm.executeUpdate();
                return "User deleted successfully.";
            } else {
                return "Invalid user.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting user: " + e.getMessage();
        }
    }
}
