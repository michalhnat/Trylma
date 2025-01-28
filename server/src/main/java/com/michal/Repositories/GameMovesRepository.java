package com.michal.Repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.michal.Models.GameMoves;

@Repository
public interface GameMovesRepository extends JpaRepository<GameMoves, Long> {
    Optional<GameMoves> findTopByGame_IdOrderByMoveNumberDesc(Long gameId);
}
