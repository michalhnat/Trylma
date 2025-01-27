package com.michal.Models;

import java.time.LocalDateTime;
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

    private int player_count;

    // private int in_game_id; // to reconsider

    private String layout;

    private String variant;

    private String[] playing_colors;

    private String state;

    private String player_taking_next_move;

    private int save_count;

    public void setPlayer_count(int player_count) {
        this.player_count = player_count;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public void setPlaying_colors(String[] playing_colors) {
        this.playing_colors = playing_colors;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPlayer_taking_next_move(String player_taking_next_move) {
        this.player_taking_next_move = player_taking_next_move;
    }

    public void setSave_count(int save_count) {
        this.save_count = save_count;
    }

    public int getPlayer_count() {
        return player_count;
    }

    public String getLayout() {
        return layout;
    }

    public String getVariant() {
        return variant;
    }

    public String[] getPlaying_colors() {
        return playing_colors;
    }

    public String getState() {
        return state;
    }

    public String getPlayer_taking_next_move() {
        return player_taking_next_move;
    }

    public int getSave_count() {
        return save_count;
    }



}


