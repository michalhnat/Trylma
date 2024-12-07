package com.michal.Commands;

import java.io.ObjectOutputStream;

import com.michal.ICommunication;

import picocli.CommandLine.Parameters;

public class JoinGameCommand extends AbstractCommand {

    public JoinGameCommand(ICommunication communication) {
        super(communication);
    }

    @Parameters(index = "0", description = "Game ID")
    private int gameID;

    @Override
    public void run() {
        if (!communication.isConnected()) {
            System.out.println("Not connected to a server");
            return;
        }
        try {
            communication.sendMessage("join " + gameID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
