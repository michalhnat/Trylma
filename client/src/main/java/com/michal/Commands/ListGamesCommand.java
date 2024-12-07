package com.michal.Commands;

import java.io.ObjectOutputStream;

import com.michal.ICommunication;

import picocli.CommandLine.Command;

@Command(name = "list", description = "List all games")

public class ListGamesCommand extends AbstractCommand {

    public ListGamesCommand(ICommunication communication) {
        super(communication);
    }

    @Override
    public void run() {
        try {
            communication.sendMessage("list");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
