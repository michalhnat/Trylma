package com.michal.Game;


/**
 * Represents a node on the game board.
 */
public class Node {
    private Pawn pawn;   // null if no pawn is on this node
    private Player owner; // If the node is a base, this is the player that owns it. Null if it's in the center

    /**
     * Constructs a Node with no pawn and no owner.
     */
    public Node() {
        this.pawn = null;
        this.owner = null;
    }

    /**
     * Copy constructor for creating a new Node based on an existing one.
     *
     * @param node the node to copy
     */
    public Node(Node node) {
        this.pawn = node.pawn != null ? new Pawn(node.pawn.getPlayer()) : null;
        this.owner = node.owner != null ? new Player(node.owner) : null;
    }

    /**
     * Returns the pawn on this node.
     *
     * @return the pawn on this node, or null if there is no pawn
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * Sets the pawn on this node.
     *
     * @param pawn the pawn to set
     */
    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    /**
     * Sets the owner of this node.
     *
     * @param owner the player to set as the owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Returns the owner of this node.
     *
     * @return the player who owns this node, or null if there is no owner
     */
    public Player getOwner() {
        return owner;
    }
}