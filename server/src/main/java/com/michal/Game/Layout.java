package com.michal.Game;

/**
 * Enum representing different game layouts with a specified number of players.
 */
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

    /**
     * Constructs a Layout with the specified number of players.
     *
     * @param players the number of players for this layout
     */
    Layout(int players) {
        this.players = players;
    }

    /**
     * Returns the number of players for this layout.
     *
     * @return the number of players
     */
    public int getPlayers() {
        return players;
    }
}