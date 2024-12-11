package brauard.RequestService.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import Controller.Connexion.ConnexionDataBase;

public class Prop {
	
	// M�thode pour enregistrer une nouvelle t�che
		public static void createNewProp(String description, int UserID) {

		    String query = "INSERT INTO Prop (ProprioID, Status, Description) VALUES (?, ?, ?)";	
		    
		    try (Connection connection = ConnexionDataBase.getConnexionDataBase();
		        PreparedStatement preparedStatement = connection.prepareStatement(query)) {

		        // Param�trer les valeurs
		        preparedStatement.setInt(1, UserID);
		        preparedStatement.setString(2,"Non effectu�e");
		        preparedStatement.setString(3, description);

		        // Ex�cuter la requ�te
		        int rowCount = preparedStatement.executeUpdate();

		        // V�rifier si l'enregistrement a r�ussi
		        if (rowCount > 0) {
		            System.out.println("Propostion enregistr�e ! Quelqu'un aura s�rement besoin de vtre aide");
		        } else {
		            System.out.println("�chec de l'enregistrement de la proposition. Veuillez r�essayer.");
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}		
}
