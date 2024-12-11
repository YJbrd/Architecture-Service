package brauard.RequestService.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import Controller.Connexion.ConnexionDataBase;

public class Task {
	
//	public boolean isValidated() {
//		//ajouter conditions sp�cifiques
//		return false;
//	}
	
	// M�thode pour enregistrer une nouvelle t�che
		public static void createNewTask(String description, int UserID, String dateLimite) {

		    String query = "INSERT INTO Task (ProprioID, Status, Description, DateLimite, Validation) VALUES (?, ?, ?, ?, ?)";	
		    
		    try (Connection connection = ConnexionDataBase.getConnexionDataBase();
		        PreparedStatement preparedStatement = connection.prepareStatement(query)) {

		        // Param�trer les valeurs
		        preparedStatement.setInt(1, UserID);
		        preparedStatement.setString(2,"Non effectu�e");
		        preparedStatement.setString(3, description);
		        preparedStatement.setString(4, dateLimite);
		        preparedStatement.setString(5,"En attente");
		        

		        // Ex�cuter la requ�te
		        int rowCount = preparedStatement.executeUpdate();

		        // V�rifier si l'enregistrement a r�ussi
		        if (rowCount > 0) {
		            System.out.println("T�che enregistr�e ! Quelqu'un va bient�t venir vous aider");
		        } else {
		            System.out.println("�chec de l'enregistrement de la t�che. Veuillez r�essayer.");
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		public static void updateStatus(int taskId, String newStatus) {
	        String query = "UPDATE Task SET Status = ? WHERE ID_Task = ?";
	        try (Connection connection = ConnexionDataBase.getConnexionDataBase();
	            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	            preparedStatement.setString(1, newStatus);
	            preparedStatement.setInt(2, taskId);

	            int rowCount = preparedStatement.executeUpdate();

	            if (rowCount > 0) {
	                System.out.println("Mise � jour du status r�ussie !");
	            } else {
	                System.out.println("�chec de la mise � jour du status. La demande pourrait ne pas exister.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		
		public static void updateValidation(int taskId, String newValidation) {
	        String query = "UPDATE Task SET Validation = ? WHERE ID_Task = ?";
	        try (Connection connection = ConnexionDataBase.getConnexionDataBase();
	            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	            preparedStatement.setString(1, newValidation);
	            preparedStatement.setInt(2, taskId);

	            int rowCount = preparedStatement.executeUpdate();

	            if (rowCount > 0) {
	                System.out.println("Mise � jour de la validation r�ussie !");
	            } else {
	                System.out.println("�chec de la mise � jour de la validation. La demande pourrait ne pas exister.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
