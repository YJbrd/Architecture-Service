package fr.insa.brauard.BDDConnexionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = "fr.insa.brauard.BDDConnexionService")
public class BddConnexionServiceApplication {

    @Bean(name = "bddRestTemplate") 
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(BddConnexionServiceApplication.class, args);
    }
}
