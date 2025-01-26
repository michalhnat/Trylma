package com.michal;

import com.michal.Database.DatabaseConnector;
import com.michal.Models.GameModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.time.LocalDateTime;

@SpringBootApplication
@EntityScan(basePackages = {"com.michal.Models"})
@ComponentScan(
        basePackages = {"com.michal.Models", "com.michal.Repositories", "com.michal.Database"})

@EnableJpaRepositories(basePackages = "com.michal.Repositories")
public class App {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);

        DatabaseConnector databaseConnector = context.getBean(DatabaseConnector.class);

        // Create a new GameModel object
        GameModel game = new GameModel();
        game.setPlayer_count(2);
        game.setStartTime(LocalDateTime.now());
        game.setEndTime(LocalDateTime.now().plusHours(1));

        // Save the GameModel object to the database
        databaseConnector.saveGame(game);

        // Start the server
        Server server = new Server();
        server.startServer();



        System.out.println("Game saved to the database.");
    }
}
