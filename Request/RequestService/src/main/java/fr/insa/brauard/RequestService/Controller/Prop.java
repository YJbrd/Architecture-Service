package Model.Tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import Controller.Connexion.ConnexionDataBase;

public class Prop {
	
	// Méthode pour enregistrer une nouvelle tâche
		public static void createNewProp(String description, int UserID) {

		    String query = "INSERT INTO Prop (ProprioID, Status, Description) VALUES (?, ?, ?)";	
		    
		    try (Connection connection = ConnexionDataBase.getConnexionDataBase();
		        PreparedStatement preparedStatement = connection.prepareStatement(query)) {

		        // Paramétrer les valeurs
		        preparedStatement.setInt(1, UserID);
		        preparedStatement.setString(2,"Non effectuée");
		        preparedStatement.setString(3, description);

		        // Exécuter la requête
		        int rowCount = preparedStatement.executeUpdate();

		        // Vérifier si l'enregistrement a réussi
		        if (rowCount > 0) {
		            System.out.println("Propostion enregistrée ! Quelqu'un aura sûrement besoin de vtre aide");
		        } else {
		            System.out.println("Échec de l'enregistrement de la proposition. Veuillez réessayer.");
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}		
}
