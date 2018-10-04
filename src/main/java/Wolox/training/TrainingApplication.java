package Wolox.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("org.baeldung.persistence.repo")
@EntityScan("org.baeldung.persistence.model")
@SpringBootApplication
public class TrainingApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        SpringApplication.run(TrainingApplication.class, args);
    }
}