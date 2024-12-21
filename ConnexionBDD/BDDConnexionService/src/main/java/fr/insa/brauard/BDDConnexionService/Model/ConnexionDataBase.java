package fr.insa.brauard.BDDConnexionService.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class ConnexionDataBase {

    @Autowired
    private RestTemplate restTemplate;

    public Connection getConnection() throws SQLException {
        // Récupération des paramètres de configuration via ConfigurationService
        String dbHost = restTemplate.getForObject("http://ConfigurationService/config/host", String.class);
        String dbPort = restTemplate.getForObject("http://ConfigurationService/config/port", String.class);
        String dbProject = restTemplate.getForObject("http://ConfigurationService/config/project", String.class);
        String dbUser = restTemplate.getForObject("http://ConfigurationService/config/username", String.class);
        String dbPassword = restTemplate.getForObject("http://ConfigurationService/config/password", String.class);
        
        // Connexion à la base de données et renvoi de la connexion
        return DriverManager.getConnection(
                "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbProject + "?serverTimezone=Europe/Paris", 
                dbUser, dbPassword);
    }
}
