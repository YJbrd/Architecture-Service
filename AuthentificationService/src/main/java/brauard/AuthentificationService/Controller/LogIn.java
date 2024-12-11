package brauard.AuthentificationService.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LogIn{
	
	public static boolean seConnecter(String nomUtilisateur, String motDePasse) {
        String query = "SELECT * FROM User WHERE UserPseudo = ? AND UserPassword = ?";
        try (Connection connection = ConnexionDataBase.getConnexionDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Param�trer les valeurs
            preparedStatement.setString(1, nomUtilisateur);
            preparedStatement.setString(2, motDePasse);

            // Ex�cuter la requ�te
            ResultSet resultSet = preparedStatement.executeQuery();

            // V�rifier si l'utilisateur existe dans la base de donn�es
            if (resultSet.next()) {
                System.out.println("Connexion utilisateur reussie !");
                return true;
            } else {
                System.out.println("Echec de la connexion. Veuillez verifier vos informations d'identification.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getTypeUtilisateur(String nomUtilisateur) {
        String typeUtilisateur = null;

        String query = "SELECT UserType FROM User WHERE UserPseudo = ?";
        try (Connection connection = ConnexionDataBase.getConnexionDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Param�trer la valeur du nom d'utilisateur
            preparedStatement.setString(1, nomUtilisateur);

            // Ex�cuter la requ�te
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Si un enregistrement est trouv�, r�cup�rer le type d'utilisateur
                if (resultSet.next()) {
                    typeUtilisateur = resultSet.getString("UserType");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return typeUtilisateur;
    }
    
    public static int getIDUtilisateur(String nomUtilisateur) {
        int IDUtilisateur = 0;

        String query = "SELECT UserID FROM User WHERE UserPseudo = ?";
        try (Connection connection = ConnexionDataBase.getConnexionDataBase();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Param�trer la valeur du nom d'utilisateur
            preparedStatement.setString(1, nomUtilisateur);

            // Ex�cuter la requ�te
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Si un enregistrement est trouv�, r�cup�rer l' ID de l'utilisateru
                if (resultSet.next()) {
                    IDUtilisateur = resultSet.getInt("UserID");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return IDUtilisateur;
    }
}
