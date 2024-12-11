package brauard.AuthentificationService.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register {

	// M�thode pour enregistrer un nouvel utilisateur
	public static void enregistrerUtilisateur(String nomUtilisateur, String motDePasse, String typeUtilisateur) {
	    String query = "INSERT INTO User (UserPseudo, UserPassword, UserType) VALUES (?, ?, ?)";
	    try (Connection connection = ConnexionDataBase.getConnexionDataBase();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	        // Param�trer les valeurs
	        preparedStatement.setString(1, nomUtilisateur);
	        preparedStatement.setString(2, motDePasse);
	        preparedStatement.setString(3, typeUtilisateur);

	        // Ex�cuter la requ�te
	        int rowCount = preparedStatement.executeUpdate();

	        // V�rifier si l'enregistrement a r�ussi
	        if (rowCount > 0) {
	            System.out.println("Enregistrement reussi !");
	        } else {
	            System.out.println("Echec de l'enregistrement. Veuillez reessayer.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
