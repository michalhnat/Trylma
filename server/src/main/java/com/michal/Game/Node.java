package com.michal.Game;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private final Integer x;
    private final Integer y;
    private Pawn pawn;   // null if no pawn is on this node
    private Player owner; // If the node is a base, this is the player that owns it. Null if its in the center

    public Node(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.pawn = null;
        this.owner = null;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}
