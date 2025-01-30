package com.michal.Game;

import com.michal.Game.Board.CornerNode;
import com.michal.Game.Board.Layout;
import com.michal.Game.Board.Node;
import com.michal.Game.Board.Pawn;
import com.michal.Utils.StarBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GameMechanicsTest {
    Node[][] boardArray = StarBuilder.buildStar(5);

    @Test
    public void winningTest() {
        Player testPlayer = new Player(UUID.randomUUID(), null);
        testPlayer.setColor("Red");

        for (int x = 0; x < boardArray.length; x++) {
            for (int y = 0; y < boardArray[x].length; y++) {
                if (boardArray[x][y] != null) {
                    if (boardArray[x][y] instanceof CornerNode cornerNode && cornerNode.getDirection() == Direction.NORTH) {
                        cornerNode.setOwner(testPlayer);
                    }
                }
            }
        }

        for (int x = 0; x < boardArray.length; x++) {
            for (int y = 0; y < boardArray[x].length; y++) {
                if (boardArray[x][y] != null) {
                    if (boardArray[x][y] instanceof CornerNode cornerNode && cornerNode.getDirection() == Direction.SOUTH) {
                        cornerNode.setPawn(new Pawn(testPlayer));
                    }
                }
            }
        }

        Player winner = checkIfSomeoneWon(List.of(testPlayer), boardArray);
        Assertions.assertEquals(testPlayer, winner);

    }

    public Player checkIfSomeoneWon(List<Player> players, Node[][] board) {
        // Map all directions to their opposites
        Map<Direction, Direction> oppositeDirections = Map.of(
                Direction.SOUTH, Direction.NORTH,
                Direction.NORTH, Direction.SOUTH,
                Direction.SOUTHWEST, Direction.NORTHEAST,
                Direction.NORTHEAST, Direction.SOUTHWEST,
                Direction.NORTHWEST, Direction.SOUTHEAST,
                Direction.SOUTHEAST, Direction.NORTHWEST
        );

        // For each player, check if they won
        for (Player p : players) {
            String playerColor = p.getColor();
            HashSet<Direction> playerDestinations = new HashSet<>();
            for (Node[] row : board) {
                for (Node node : row) {
                    if (node instanceof CornerNode cornerNode && cornerNode.getOwner() == p) {
                        playerDestinations.add(oppositeDirections.get(cornerNode.getDirection()));
                        break;
                    }
                }
            }

            // Check every corner that is the destination of the player. If all of them have a pawn of the player color, the player won
            boolean won = true;
            for (Node[] row : board) {
                for (Node node : row) {
                    if (node instanceof CornerNode cornerNode && playerDestinations.contains(cornerNode.getDirection())) {
                        if (cornerNode.getPawn() == null || !Objects.equals(cornerNode.getPawn().getPlayer().getColor(), playerColor)) {
                            won = false;
                            break;
                        }
                    }
                }
            }

            if (won) {
                return p;
            }
        }

        return null;
    }
}
