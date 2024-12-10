package com.michal.Game;

/**
 * Represents the status of the game.
 */
public enum GameStatus {
    /**
     * Indicates that the game is waiting for players to join.
     */
    WAITING,

    /**
     * Indicates that the game is currently in progress.
     */
    IN_PROGRESS,

    /**
     * Indicates that the game has finished.
     */
    FINISHED
}