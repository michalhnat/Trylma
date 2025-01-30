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

    /**
     * Saves the game model to the database.
     *
     * @param game the game model to save
     */
    @Transactional
    @Retryable(value = OptimisticLockException.class, maxAttempts = 3)
    public void saveGame(GameModel game) {
        gameRepository.save(game);
    }

    /**
     * Saves the game move to the database.
     *
     * @param gameMove the game move to save
     */
    @Transactional
    @Retryable(value = OptimisticLockException.class, maxAttempts = 3)
    public void saveGameMove(GameMoves gameMove) {
        gameMovesRepository.save(gameMove);
    }

    /**
     * Retrieves all game models from the database.
     *
     * @return a list of all game models
     */
    public List<GameModel> getAllGames() {
        return gameRepository.findAll();
    }

    /**
     * Retrieves the last game move for a specific game.
     *
     * @param gameId the ID of the game
     * @return an optional containing the last game move, or empty if not found
     */
    public Optional<GameMoves> getLastGameMove(Long gameId) {
        return gameMovesRepository.findTopByGame_IdOrderByMoveNumberDesc(gameId);
    }

    /**
     * Retrieves all game moves for a specific game.
     *
     * @param gameId the ID of the game
     * @return an optional containing a list of game moves, or empty if not found
     */
    public Optional<List<GameMoves>> getGameMoves(long gameId) {
        return gameMovesRepository.findByGame_Id(gameId);
    }
}