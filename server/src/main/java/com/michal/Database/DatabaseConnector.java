package com.michal.Database;

import com.michal.Models.GameModel;
import com.michal.Models.GameMoves;
import com.michal.Repositories.GameRepository;
import com.michal.Repositories.GameMovesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnector {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameMovesRepository gameMovesRepository;

    public void saveGame(GameModel game) {
        gameRepository.save(game);
    }

    public void saveGameMove(GameMoves gameMove) {
        gameMovesRepository.save(gameMove);
    }

    // Add more methods as needed
}
