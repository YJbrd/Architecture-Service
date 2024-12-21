package fr.insa.brauard.AuthentificationService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.brauard.AuthentificationService.Model.User;
import fr.insa.brauard.BDDConnexionService.Model.ConnexionDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class LogIn {

    @Autowired
    private ConnexionDataBase databaseConnectionService;

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        String username = loginRequest.getUserPseudo();
        String password = loginRequest.getUserPassword();

        try (Connection con = databaseConnectionService.getConnection()) {
            String query = "SELECT * FROM User WHERE UserPseudo = ? AND UserPassword = ?";
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, username);
            stm.setString(2, password);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return "Login successful! Welcome, " + rs.getString("UserPseudo");
            } else {
                return "Invalid username or password.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error during login process: " + e.getMessage();
        }
    }
}
