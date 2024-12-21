package fr.insa.brauard.RequestService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {"fr.insa.brauard.RequestService", 
		"fr.insa.brauard.BDDConnexionService",
		"fr.insa.brauard.AuthentificationService"})
public class RequestServiceApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(RequestServiceApplication.class, args);
	}

}
