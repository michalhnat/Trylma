package com.michal.Game;

public enum Layout {
    SIXPLAYERS(6),
    FOURPLAYERS(4),
    THREEPLAYERS_ONESET(3),
    THREEPLAYERS_TWOSETS(3),
    TWOPLAYERS_ONESET(2),
    TWOPLAYERS_TWOSETS_ADJACENT(2),
    TWOPLAYERS_TWOSETS_OPPOSITE(2),
    TWOPLAYERS_THREESETS(2);

    private final int players;

    Layout(int players) {
        this.players = players;
    }

    public int getPlayers() {
        return players;
    }
}