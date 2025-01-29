package com.michal.Database;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import com.michal.Models.GameModel;
import com.michal.Models.GameMoves;
import com.michal.Repositories.GameMovesRepository;
import com.michal.Repositories.GameRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

@Component
public class DatabaseConnector {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameMovesRepository gameMovesRepository;


    @Transactional
    @Retryable(value = OptimisticLockException.class, maxAttempts = 3)
    public void saveGame(GameModel game) {
        gameRepository.save(game);
    }


    @Transactional
    @Retryable(value = OptimisticLockException.class, maxAttempts = 3)
    public void saveGameMove(GameMoves gameMove) {
        gameMovesRepository.save(gameMove);
    }

    public List<GameModel> getAllGames() {
        return gameRepository.findAll();
    }

    public Optional<GameMoves> getLastGameMove(Long gameId) {
        return gameMovesRepository.findTopByGame_IdOrderByMoveNumberDesc(gameId);
    }

    public Optional<List<GameMoves>> getGameMoves(long gameId) {
        return gameMovesRepository.findByGame_Id(gameId);
    }
}
