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
        String dbHost = restTemplate.getForObject("http://localhost:8082/config/host", String.class);
        String dbPort = restTemplate.getForObject("http://localhost:8082/config/port", String.class);
        String dbProject = restTemplate.getForObject("http://localhost:8082/config/project", String.class);
        String dbUser = restTemplate.getForObject("http://localhost:8082/config/username", String.class);
        String dbPassword = restTemplate.getForObject("http://localhost:8082/config/password", String.class);
        
        // Connexion à la base de données et renvoi de la connexion
        return DriverManager.getConnection(
                "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbProject + "?serverTimezone=Europe/Paris", 
                dbUser, dbPassword);
    }
}
