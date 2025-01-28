package com.michal.Models;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

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

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public void setPlayingColors(String[] playingColors) {
        this.playingColors = playingColors;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPlayerTakingNextMove(String playerTakingNextMove) {
        this.playerTakingNextMove = playerTakingNextMove;
    }

    public void setSaveCount(int saveCount) {
        this.saveCount = saveCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public String getLayout() {
        return layout;
    }

    public Long getId() {
        return id;
    }

    public String getVariant() {
        return variant;
    }

    public String[] getPlayingColors() {
        return playingColors;
    }

    public String getState() {
        return state;
    }

    public String getPlayerTakingNextMove() {
        return playerTakingNextMove;
    }

    public int getSaveCount() {
        return saveCount;
    }



}


