package com.michal.Repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.michal.Models.GameMoves;

/**
 * Repository interface for accessing and managing GameMoves entities.
 */
@Repository
public interface GameMovesRepository extends JpaRepository<GameMoves, Long> {

    /**
     * Finds the most recent GameMoves entry for a given game, ordered by move number in descending order.
     *
     * @param gameId the ID of the game
     * @return an Optional containing the most recent GameMoves entry, or an empty Optional if none found
     */
    Optional<GameMoves> findTopByGame_IdOrderByMoveNumberDesc(Long gameId);

    /**
     * Finds all GameMoves entries for a given game.
     *
     * @param gameId the ID of the game
     * @return an Optional containing a list of GameMoves entries, or an empty Optional if none found
     */
    Optional<List<GameMoves>> findByGame_Id(long gameId);
}