package Controller.DataUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import Controller.Connexion.ConnexionDataBase;

public class DataUsers {
	
	private static String queryDemandes= null;
	private static String queryResultat= "Vous n'avez enregistré aucune demande d'aide";

	
	private static DefaultListModel<String> listeDemandesModel=new DefaultListModel<>();

	public static DefaultListModel<String> getTasksList(int IDUser){			
		
		queryDemandes = "SELECT * FROM Task WHERE ProprioID = ?";
        try (Connection connection = ConnexionDataBase.getConnexionDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(queryDemandes)){;
	        // Paramétrer la valeur du nom d'utilisateur
	        preparedStatement.setInt(1, IDUser);

            // Exécuter la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                
                while (resultSet.next()) {
                	// Si un enregistrement est trouvé, récupérer les tâches enregistrées
                	
                	String desc = resultSet.getString("Description");
	            	int num = resultSet.getInt("ID_Task"); 
	            	String status = resultSet.getString("Status");
	            	String valid = resultSet.getString("Validation");
	            	queryResultat = num + " - " + valid + " - " + status + " - " + desc;
	            	System.out.println(queryResultat);
                	listeDemandesModel.addElement(queryResultat);
                }
            }
        } catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
        }
		return listeDemandesModel;

    }	
       
	public static DefaultListModel<String> getTasksListAll(){
				
		queryDemandes = "SELECT * FROM Task";

        try (Connection connection = ConnexionDataBase.getConnexionDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(queryDemandes)){;

            // Exécuter la requête
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                
                while (resultSet.next()) {
	            	String desc = resultSet.getString("Description");
	            	int num = resultSet.getInt("ID_Task"); 
	            	String status = resultSet.getString("Status");
	            	queryResultat = num +" - " + status + " - " + desc;
                	listeDemandesModel.addElement(queryResultat);
                }
            }
        } catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
        }
		return listeDemandesModel;
    }
	
	public static int getTaskCount(){
		
		String queryCount = "SELECT COUNT(*) FROM Task";

        try (Connection connection = ConnexionDataBase.getConnexionDataBase();
            PreparedStatement preparedStatement = connection.prepareStatement(queryCount)){;

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                
            	if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
        }
		return 0;

    }
	
}

        

	