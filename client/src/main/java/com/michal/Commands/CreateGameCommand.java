package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;
import com.michal.Exceptions.FailedSendingMessageToServer;
import com.michal.Utils.JsonBuilder;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

/**
 * Command to create a game.
 * Implements the Runnable interface to be executed as a command.
 */
@Command(name = "create", description = "Create a game")
public class CreateGameCommand implements Runnable {

    @ParentCommand
    private MainCommand parent;

    @Parameters(index = "0", description = "Layout of the game")
    private String layout;

    @Parameters(index = "1", description = "Variant of the game")
    private String variant;

    @Parameters(index = "2", description = "Size of the board")
    private int boardSize;

    /**
     * Executes the command to create a game.
     * Sends a JSON message to the server with the number of players.
     */
    @Override
    public void run() {
        Display display = parent.getDisplay();
        ICommunication communication = parent.getCommunication();
        if (!communication.isConnected()) {
            display.displayError("Not connected to a server");
            return;
        }
        try {
            String jsonMessage =
                    JsonBuilder.setBuilder("create").setPayloadArgument("layout", layout)
                            .setPayloadArgument("variant", variant)
                            .setPayloadArgument("boardSize", boardSize).build();
            communication.sendMessage(jsonMessage);
        } catch (FailedSendingMessageToServer e) {
            display.displayError(e.getMessage());
        } catch (Exception e) {
            display.displayError("Failed to join game");
        }
    }

}