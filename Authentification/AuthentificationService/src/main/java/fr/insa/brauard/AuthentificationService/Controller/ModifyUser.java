package fr.insa.brauard.AuthentificationService.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.*;

@RestController
@RequestMapping("/user")
public class ModifyUser {

	@Autowired
	private RestTemplate restTemplate;

	@PutMapping("/modify/{id}")
	public void modifyUser(@PathVariable int id, @RequestParam String name, @RequestParam String city, @RequestParam int idSender) {
	    String dbHost = restTemplate.getForObject("http://localhost:8082/config/host", String.class);
	    String dbPort = restTemplate.getForObject("http://localhost:8082/config/port", String.class);
	    String dbProject = restTemplate.getForObject("http://localhost:8082/config/project", String.class);
	    String dbUser = restTemplate.getForObject("http://localhost:8082/config/username", String.class);
	    String dbPassword = restTemplate.getForObject("http://localhost:8082/config/password", String.class);

	    try (Connection con = DriverManager.getConnection(
	            "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbProject + "?serverTimezone=Europe/Paris", 
	            dbUser, dbPassword)) {
	        
	        // Vérification si l'utilisateur est admin ou si c'est l'utilisateur qui modifie son propre compte
	        Statement stm = con.createStatement();
	        ResultSet rs = stm.executeQuery("SELECT UserType FROM User WHERE UserID = " + idSender + ";");
	        ResultSet rs1 = stm.executeQuery("SELECT UserID FROM User WHERE UserID = " + id + ";");

	        if (rs.next() && rs1.next()) {
	            // Si l'utilisateur est admin ou c'est lui-même qui veut modifier ses informations
	            if ("Admin".equalsIgnoreCase(rs.getString("UserType")) || rs1.getInt("UserID") == idSender) {
	                PreparedStatement stm2 = con.prepareStatement("UPDATE User SET UserPseudo = ?, UserPassword = ?, UserReview = ?, UserType = ? WHERE UserID = ?");
	                stm2.setString(1, name);
	                stm2.setString(2, "defaultPassword"); // Si vous voulez donner un mot de passe par défaut pour le test, sinon utilisez un paramètre
	                stm2.setString(3, ""); // Laisser vide ou gérer la logique du champ UserReview
	                stm2.setString(4, "Vulnerable"); // Mettre ici un rôle par défaut ou passer le rôle comme paramètre
	                stm2.setInt(5, id);
	                stm2.executeUpdate();
	                System.out.println("User with ID " + id + " updated successfully.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


}
