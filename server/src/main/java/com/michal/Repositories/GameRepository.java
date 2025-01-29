package com.michal.Repositories;

import com.michal.Models.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing GameModel entities.
 */
@Repository
public interface GameRepository extends JpaRepository<GameModel, Long> {
}