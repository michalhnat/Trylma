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

    // @Column(name = "game")
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

    // @Lob
    @Column(name = "boardAfterMove", columnDefinition = "TEXT", length = 65536)
    private String boardAfterMove;

    @Column(name = "playerColor")
    private String playerColor;
    // private LocalDateTime move_time;


    public void setId(Long id) {
        this.id = id;
    }

    public void setGame(GameModel game) {
        this.game = game;
    }


    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public void setBoardAfterMove(String boardAfterMove) {
        this.boardAfterMove = boardAfterMove;
    }


    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    // public void setMoveTime(LocalDateTime move_time) {
    // this.move_time = move_time;
    // }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public GameModel getGame() {
        return game;
    }

    public Long getVersion() {
        return version;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public String getBoardAfterMove() {
        return boardAfterMove;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
