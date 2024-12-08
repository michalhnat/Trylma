package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;

import picocli.CommandLine.Command;

@Command(name = "", subcommands = {
        ConnectToServerCommand.class,
        ListGamesCommand.class,
        JoinGameCommand.class,
        MoveCommand.class,
        HelpCommand.class,
        ExitCommand.class
})
public class MainCommand implements Runnable {
    protected ICommunication communication;
    protected Display display;

    public MainCommand(ICommunication communication, Display display) {
        this.communication = communication;
        this.display = display;
    }

    public ICommunication getCommunication() {
        return communication;
    }

    public Display getDisplay() {
        return display;
    }

    @Override
    public void run() {
    }
}