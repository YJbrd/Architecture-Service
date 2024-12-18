package fr.insa.brauard.AuthentificationService.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import fr.insa.brauard.AuthentificationService.Model.User;

@RestController
public class LogIn {

    @Autowired
    private RestTemplate restTemplate;

    // Représente la requête de login, on attend un corps JSON avec username et password
    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        String username = loginRequest.getUserPseudo();
        String password = loginRequest.getUserPassword();

        // Récupérer les paramètres de connexion de la base de données depuis ConfigurationService
        String dbHost = restTemplate.getForObject("http://localhost:8082/config/host", String.class);
        String dbPort = restTemplate.getForObject("http://localhost:8082/config/port", String.class);
        String dbProject = restTemplate.getForObject("http://localhost:8082/config/project", String.class);
        String dbUser = restTemplate.getForObject("http://localhost:8082/config/username", String.class);
        String dbPassword = restTemplate.getForObject("http://localhost:8082/config/password", String.class);

        // Connexion à la base de données
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbProject + "?serverTimezone=Europe/Paris", 
                dbUser, dbPassword)) {

            // Préparation de la requête SQL pour vérifier les identifiants
            String query = "SELECT * FROM User WHERE UserPseudo = ? AND UserPassword = ?";
            PreparedStatement stm = con.prepareStatement(query);
            stm.setString(1, username);
            stm.setString(2, password);

            // Exécution de la requête
            ResultSet rs = stm.executeQuery();

            // Si un utilisateur est trouvé avec ces identifiants
            if (rs.next()) {
                // On peut maintenant créer un objet User à partir des résultats de la base de données
                int userId = rs.getInt("UserID");
                String userReview = rs.getString("UserReview");
                String userType = rs.getString("UserType");

                // Création de l'objet User avec les informations de la base de données
                User authenticatedUser = new User(userId, username, password, userReview, userType);

                // Vous pouvez retourner un message de succès ou un token d'authentification ici
                return "Login successful! Welcome, " + authenticatedUser.getUserPseudo();
            } else {
                // Retourner un message d'erreur si l'utilisateur n'a pas été trouvé
                return "Invalid username or password.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error during login process.";
        }
    }
}
