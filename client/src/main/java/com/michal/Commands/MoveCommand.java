package com.michal.Commands;

import java.io.ObjectOutputStream;

import com.michal.ICommunication;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "move", description = "Move to a specific position")

public class MoveCommand extends AbstractCommand {

    public MoveCommand(ICommunication communication) {
        super(communication);
    }

    @Parameters(index = "0", description = "X coordinate")
    private int x;

    @Parameters(index = "1", description = "Y coordinate")
    private int y;

    @Override
    public void run() {
        try {
            communication.sendMessage("move " + x + " " + y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
