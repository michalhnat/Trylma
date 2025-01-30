package com.michal.Models;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * Entity representing a game.
 */
@Entity
@Table(name = "game")
public class GameModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(name = "playerCount")
    private int playerCount;

    // private int in_game_id; // to reconsider

    @Column(name = "layout")
    private String layout;

    @Column(name = "variant")
    private String variant;

    @Column(name = "playingColors")
    private String[] playingColors;

    @Column(name = "state")
    private String state;

    @Column(name = "playerTakingNextMove")
    private String playerTakingNextMove;

    @Column(name = "saveCount")
    private int saveCount;

    /**
     * Sets the player count.
     *
     * @param playerCount the player count to set
     */
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    /**
     * Sets the layout of the game.
     *
     * @param layout the layout to set
     */
    public void setLayout(String layout) {
        this.layout = layout;
    }

    /**
     * Sets the variant of the game.
     *
     * @param variant the variant to set
     */
    public void setVariant(String variant) {
        this.variant = variant;
    }

    /**
     * Sets the playing colors.
     *
     * @param playingColors the playing colors to set
     */
    public void setPlayingColors(String[] playingColors) {
        this.playingColors = playingColors;
    }

    /**
     * Sets the state of the game.
     *
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Sets the player taking the next move.
     *
     * @param playerTakingNextMove the player taking the next move to set
     */
    public void setPlayerTakingNextMove(String playerTakingNextMove) {
        this.playerTakingNextMove = playerTakingNextMove;
    }

    /**
     * Sets the save count.
     *
     * @param saveCount the save count to set
     */
    public void setSaveCount(int saveCount) {
        this.saveCount = saveCount;
    }

    /**
     * Returns the player count.
     *
     * @return the player count
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Returns the layout of the game.
     *
     * @return the layout
     */
    public String getLayout() {
        return layout;
    }

    /**
     * Returns the ID of the game.
     *
     * @return the ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the variant of the game.
     *
     * @return the variant
     */
    public String getVariant() {
        return variant;
    }

    /**
     * Returns the playing colors.
     *
     * @return the playing colors
     */
    public String[] getPlayingColors() {
        return playingColors;
    }

    /**
     * Returns the state of the game.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Returns the player taking the next move.
     *
     * @return the player taking the next move
     */
    public String getPlayerTakingNextMove() {
        return playerTakingNextMove;
    }

    /**
     * Returns the save count.
     *
     * @return the save count
     */
    public int getSaveCount() {
        return saveCount;
    }
}