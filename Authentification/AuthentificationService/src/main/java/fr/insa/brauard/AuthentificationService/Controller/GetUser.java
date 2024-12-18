package fr.insa.brauard.AuthentificationService.Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import fr.insa.brauard.AuthentificationService.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.sql.*;

@RestController
@RequestMapping("/user")
public class GetUser{
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getUser(@PathVariable int id) {
		User user = null;
		String dbHost = restTemplate.getForObject("http://localhost:8082/config/host", String.class);
		String dbPort = restTemplate.getForObject("http://localhost:8082/config/port", String.class);
		String dbProject = restTemplate.getForObject("http://localhost:8082/config/project", String.class);
		String dbUser = restTemplate.getForObject("http://localhost:8082/config/username", String.class);
		String dbPassword = restTemplate.getForObject("http://localhost:8082/config/password", String.class);
		
		try (Connection con = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbProject + "?serverTimezone=Europe/Paris", dbUser, dbPassword)) {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM User WHERE UserID=" + id + ";");
			if (rs.next()) {
				user = new User(
					rs.getInt("UserID"), 
					rs.getString("UserPseudo"), 
					rs.getString("UserPassword"), 
					rs.getString("UserReview"), 
					rs.getString("UserType")
				);
				// Retourner une réponse avec un code 200 (OK) et l'utilisateur trouvé
				return ResponseEntity.ok(user);
			} else {
				// Retourner une réponse avec un code 404 (Not Found) si l'utilisateur n'existe pas
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Utilisateur avec l'ID " + id + " non trouvé.");
			}
		} catch (SQLException e) {
			// Retourner une réponse avec un code 500 (Internal Server Error) en cas d'exception SQL
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
		}
	}
	
	@GetMapping("/all")
	public ArrayList<User> getAllUsers() {
	    ArrayList<User> userList = new ArrayList<User>();

	    // Récupération des paramètres de configuration via ConfigurationService
	    String dbHost = restTemplate.getForObject("http://localhost:8082/config/host", String.class);
	    String dbPort = restTemplate.getForObject("http://localhost:8082/config/port", String.class);
	    String dbProject = restTemplate.getForObject("http://localhost:8082/config/project", String.class);
	    String dbUser = restTemplate.getForObject("http://localhost:8082/config/username", String.class);
	    String dbPassword = restTemplate.getForObject("http://localhost:8082/config/password", String.class);

	    try (Connection con = DriverManager.getConnection(
	            "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbProject + "?serverTimezone=Europe/Paris", 
	            dbUser, dbPassword)) {
	        
	        // Requête pour récupérer tous les utilisateurs de la table User
	        Statement stm = con.createStatement();
	        ResultSet rs = stm.executeQuery("SELECT * FROM User;");
	        
	        // Parcours des résultats et ajout dans la liste
	        while (rs.next()) {
	            // Ajout de chaque utilisateur à la liste
	            userList.add(new User(
	                rs.getInt("UserID"),          // UserID
	                rs.getString("UserPseudo"),   // UserPseudo
	                rs.getString("UserPassword"), // UserPassword
	                rs.getString("UserReview"),   // UserReview (peut être NULL)
	                rs.getString("UserType")      // UserType
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    // Retourne la liste d'utilisateurs
	    return userList;
	}
}
