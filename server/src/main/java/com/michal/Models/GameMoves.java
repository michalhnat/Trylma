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

    private int move_number;

    private int start_x;

    private int start_y;

    private int end_x;

    private int end_y;

    // @Lob
    @Column(name = "board_after_move", columnDefinition = "TEXT", length = 65536)
    private String board_after_move;

    private String player_color;
    // private LocalDateTime move_time;

    public void setId(Long id) {
        this.id = id;
    }

    public void setGame(GameModel game) {
        this.game = game;
    }

    public void setMoveNumber(int move_number) {
        this.move_number = move_number;
    }

    public void setBoardAfterMove(String board_after_move) {
        this.board_after_move = board_after_move;
    }

    public void setPlayerColor(String player_color) {
        this.player_color = player_color;
    }

    // public void setMoveTime(LocalDateTime move_time) {
    // this.move_time = move_time;
    // }

    public void setStartX(int start_x) {
        this.start_x = start_x;
    }

    public void setStartY(int start_y) {
        this.start_y = start_y;
    }

    public void setEndX(int end_x) {
        this.end_x = end_x;
    }

    public void setEndY(int end_y) {
        this.end_y = end_y;
    }
}
