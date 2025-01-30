package com.michal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import com.michal.Database.DatabaseConnector;

/**
 * Main application class for the Spring Boot application.
 */
@SpringBootApplication
@EntityScan(basePackages = {"com.michal.Models"})
@ComponentScan(basePackages = {"com.michal"})
@EnableJpaRepositories(basePackages = "com.michal.Repositories")
@EnableRetry
public class App {

    /**
     * Main method to run the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * Initializes the server and database connector.
     *
     * @param server the server to start
     * @param databaseConnector the database connector to initialize
     * @return a CommandLineRunner to execute the initialization
     */
    @Bean
    CommandLineRunner init(Server server, DatabaseConnector databaseConnector) {
        return args -> {
            server.startServer();

            // GameModel game = new GameModel();
            // game.setPlayer_count(2);
            // game.setStartTime(LocalDateTime.now());
            // game.setEndTime(LocalDateTime.now().plusHours(1));

            // // Save the GameModel object to the database
            // databaseConnector.saveGame(game);

            // System.out.println("Game saved to the database.");
        };
    }
}