package com.michal.Models;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * Entity representing a move in a game.
 */
@Entity
@Table(name = "game_moves")
public class GameMoves {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameModel game;

    @Version
    private Long version;

    @Column(name = "moveNumber")
    private int moveNumber;

    @Column(name = "startX")
    private int startX;

    @Column(name = "startY")
    private int startY;

    @Column(name = "endX")
    private int endX;

    @Column(name = "endY")
    private int endY;

    @Column(name = "boardAfterMove", columnDefinition = "TEXT", length = 65536)
    private String boardAfterMove;

    @Column(name = "playerColor")
    private String playerColor;

    /**
     * Sets the ID of the game move.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the game associated with the move.
     *
     * @param game the game to set
     */
    public void setGame(GameModel game) {
        this.game = game;
    }

    /**
     * Sets the move number.
     *
     * @param moveNumber the move number to set
     */
    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    /**
     * Sets the board state after the move.
     *
     * @param boardAfterMove the board state to set
     */
    public void setBoardAfterMove(String boardAfterMove) {
        this.boardAfterMove = boardAfterMove;
    }

    /**
     * Sets the color of the player making the move.
     *
     * @param playerColor the player color to set
     */
    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * Sets the starting X coordinate of the move.
     *
     * @param startX the starting X coordinate to set
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * Sets the starting Y coordinate of the move.
     *
     * @param startY the starting Y coordinate to set
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * Sets the ending X coordinate of the move.
     *
     * @param endX the ending X coordinate to set
     */
    public void setEndX(int endX) {
        this.endX = endX;
    }

    /**
     * Sets the ending Y coordinate of the move.
     *
     * @param endY the ending Y coordinate to set
     */
    public void setEndY(int endY) {
        this.endY = endY;
    }

    /**
     * Returns the game associated with the move.
     *
     * @return the game
     */
    public GameModel getGame() {
        return game;
    }

    /**
     * Returns the version of the game move.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Returns the move number.
     *
     * @return the move number
     */
    public int getMoveNumber() {
        return moveNumber;
    }

    /**
     * Returns the starting X coordinate of the move.
     *
     * @return the starting X coordinate
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Returns the starting Y coordinate of the move.
     *
     * @return the starting Y coordinate
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Returns the ending X coordinate of the move.
     *
     * @return the ending X coordinate
     */
    public int getEndX() {
        return endX;
    }

    /**
     * Returns the ending Y coordinate of the move.
     *
     * @return the ending Y coordinate
     */
    public int getEndY() {
        return endY;
    }

    /**
     * Returns the board state after the move.
     *
     * @return the board state
     */
    public String getBoardAfterMove() {
        return boardAfterMove;
    }

    /**
     * Returns the color of the player making the move.
     *
     * @return the player color
     */
    public String getPlayerColor() {
        return playerColor;
    }

    /**
     * Sets the version of the game move.
     *
     * @param version the version to set
     */
    public void setVersion(Long version) {
        this.version = version;
    }
}