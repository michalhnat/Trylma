package com.michal.Game;

/**
 * Represents a pawn in the game with a color and position.
 */
public class Pawn {
    private Player player;

    public Pawn(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
