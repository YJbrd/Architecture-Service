package fr.insa.brauard.AuthentificationService.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.*;

@RestController
@RequestMapping("/user")
public class DeleteUser {

	@Autowired
	private RestTemplate restTemplate;

	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable int id, @RequestParam int idSender) {
	    String dbHost = restTemplate.getForObject("http://ConfigurationService/config/host", String.class);
	    String dbPort = restTemplate.getForObject("http://ConfigurationService/config/port", String.class);
	    String dbProject = restTemplate.getForObject("http://ConfigurationService/config/project", String.class);
	    String dbUser = restTemplate.getForObject("http://ConfigurationService/config/username", String.class);
	    String dbPassword = restTemplate.getForObject("http://ConfigurationService/config/password", String.class);

	    // Try-with-resources pour garantir la fermeture correcte des ressources
	    try (Connection con = DriverManager.getConnection(
	             "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbProject, dbUser, dbPassword)) {

	        // Utilisation de try-with-resources pour les Statement et ResultSet
	        try (Statement stm = con.createStatement();
	             ResultSet rs = stm.executeQuery("SELECT admin FROM user WHERE iduser = " + idSender + ";");
	             Statement stm1 = con.createStatement();
	             ResultSet rs1 = stm1.executeQuery("SELECT iduser FROM user WHERE iduser = " + id + ";")) {

	            if (rs.next() && rs1.next()) {
	                // Vérification des droits d'administration ou si l'utilisateur est le même que celui qui veut supprimer
	                if (rs.getBoolean("admin") || rs1.getInt("iduser") == idSender) {
	                    // Préparation de la requête DELETE
	                    try (PreparedStatement stm2 = con.prepareStatement("DELETE FROM user WHERE iduser = ?")) {
	                        stm2.setInt(1, id);
	                        stm2.executeUpdate();
	                        System.out.println("User deleted successfully!");
	                    }
	                } else {
	                    System.out.println("User not authorized to delete this user.");
	                }
	            }

	        } catch (SQLException e) {
	            System.out.println("Error in executing query: " + e.getMessage());
	            e.printStackTrace();
	        }

	    } catch (SQLException e) {
	        System.out.println("Error in DB connection: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}
