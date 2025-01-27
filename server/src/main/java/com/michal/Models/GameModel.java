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



    // private LocalDateTime startTime;

    // private LocalDateTime endTime;


    // public void setId(Long id) {
    // this.id = id;
    // }

    // public void setStartTime(LocalDateTime startTime) {
    // this.startTime = startTime;
    // }

    // public void setEndTime(LocalDateTime endTime) {
    // this.endTime = endTime;
    // }

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
    // public void setEndTime(LocalDateTime endTime) {
    // this.endTime = endTime;
    // }
}


