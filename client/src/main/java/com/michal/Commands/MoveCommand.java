package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;
import com.michal.JsonBuilder;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "move", description = "Move to a specific position")

public class MoveCommand extends AbstractCommand {

    private Display display;

    public MoveCommand(ICommunication communication, Display display) {
        super(communication, display);
    }

    @Parameters(index = "0", description = "X coordinate")
    private int x;

    @Parameters(index = "1", description = "Y coordinate")
    private int y;

    @Override
    public void run() {
        if (!communication.isConnected()) {
            display.displayMessage("Not connected to a server");
            return;
        }
        try {
            String jsonMessage = JsonBuilder.setBuilder("move")
                    .setArgument("x", x)
                    .setArgument("y", y)
                    .build();
            communication.sendMessage(jsonMessage);
        } catch (Exception e) {
            display.displayMessage("Failed to move");
        }
    }
}
