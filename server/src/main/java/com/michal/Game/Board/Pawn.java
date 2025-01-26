package com.michal.Game.Board;

import com.michal.Game.Player;

/**
 * Represents a pawn in the game with a color and position.
 */
public class Pawn {
    private Player player;

    /**
     * Constructs a Pawn with the specified player.
     *
     * @param player the player who owns the pawn
     */
    public Pawn(Player player) {
        this.player = player;
    }

    /**
     * Returns the player who owns the pawn.
     *
     * @return the player who owns the pawn
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player who owns the pawn.
     *
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}