package fr.insa.brauard.AuthentificationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@ComponentScan(basePackages = {"fr.insa.brauard.AuthentificationService", "fr.insa.brauard.BDDConnexionService", "fr.insa.brauard.RequestService"})
public class AuthentificationServiceApplication {
	
	@Bean
	@LoadBalanced
	public RestTemplate restemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AuthentificationServiceApplication.class, args);
	}

}


