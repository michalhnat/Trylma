package com.michal;

public class GameSave {
    private String id;
    private String board;

    public GameSave(String id, String board) {
        this.id = id;
        this.board = board;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }
}
