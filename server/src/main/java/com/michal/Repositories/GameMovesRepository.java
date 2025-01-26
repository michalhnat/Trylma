package com.michal.Repositories;

import com.michal.Models.GameMoves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMovesRepository extends JpaRepository<GameMoves, Long> {
}
