package com.michal;

import com.michal.Database.DatabaseConnector;
import com.michal.Models.GameModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import java.time.LocalDateTime;

@SpringBootApplication
@EntityScan(basePackages = {"com.michal.Models"})
@ComponentScan(basePackages = {"com.michal"})
@EnableJpaRepositories(basePackages = "com.michal.Repositories")
@EnableRetry
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

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
