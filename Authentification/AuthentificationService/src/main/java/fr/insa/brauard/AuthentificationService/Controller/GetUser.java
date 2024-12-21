package fr.insa.brauard.AuthentificationService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import fr.insa.brauard.AuthentificationService.Model.User;
import fr.insa.brauard.BDDConnexionService.Model.ConnexionDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class GetUser {

    @Autowired
    private ConnexionDataBase databaseConnectionService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable int id) {
        try (Connection con = databaseConnectionService.getConnection()) {
            String query = "SELECT * FROM User WHERE UserID = ?";
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                User user = new User(
                    rs.getInt("UserID"),
                    rs.getString("UserPseudo"),
                    rs.getString("UserPassword"),
                    rs.getString("UserReview"),
                    rs.getString("UserType")
                );
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving user: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try (Connection con = databaseConnectionService.getConnection()) {
            String query = "SELECT * FROM User";
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                userList.add(new User(
                    rs.getInt("UserID"),
                    rs.getString("UserPseudo"),
                    rs.getString("UserPassword"),
                    rs.getString("UserReview"),
                    rs.getString("UserType")
                ));
            }
            return ResponseEntity.ok(userList);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving users: " + e.getMessage());
        }
    }
}
