package com.michal.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_moves")
public class GameMoves {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameModel game;

    private int move_number;
    private String board_after_move;
    private int player_number;
    private LocalDateTime move_time;

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

    public void setPlayerNumber(int player_number) {
        this.player_number = player_number;
    }

    public void setMoveTime(LocalDateTime move_time) {
        this.move_time = move_time;
    }
}
