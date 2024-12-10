package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;
import com.michal.Utils.JsonBuilder;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

/**
 * Command to move to a specific position.
 * Implements the Runnable interface to be executed as a command.
 */
@Command(name = "move", description = "Move to a specific position")
public class MoveCommand implements Runnable {

    @ParentCommand
    private MainCommand parent;

    @Parameters(index = "0", description = "X coordinate")
    private int x;

    @Parameters(index = "1", description = "Y coordinate")
    private int y;

    /**
     * Executes the command to move to a specific position.
     * Sends a JSON message to the server with the coordinates.
     */
    @Override
    public void run() {
        Display display = parent.getDisplay();
        ICommunication communication = parent.getCommunication();
        if (!communication.isConnected()) {
            display.displayMessage("Not connected to a server");
            return;
        }
        try {
            String jsonMessage = JsonBuilder.setBuilder("move").setPayloadArgument("x", x)
                    .setPayloadArgument("y", y).build();
            communication.sendMessage(jsonMessage);
        } catch (Exception e) {
            display.displayMessage("Failed to move");
        }
    }
}