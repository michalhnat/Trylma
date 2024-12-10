package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;

import picocli.CommandLine.Command;

/**
 * Main command class that serves as the entry point for all subcommands.
 * Implements the Runnable interface to be executed as a command.
 */
@Command(name = "",
        subcommands = {ConnectToServerCommand.class, ListGamesCommand.class, JoinGameCommand.class,
                MoveCommand.class, HelpCommand.class, ExitCommand.class, CreateGameCommand.class})
public class MainCommand implements Runnable {
    protected ICommunication communication;
    protected Display display;

    /**
     * Constructor for MainCommand.
     *
     * @param communication the communication interface to interact with the server
     * @param display the display interface to show messages to the user
     */
    public MainCommand(ICommunication communication, Display display) {
        this.communication = communication;
        this.display = display;
    }

    /**
     * Gets the communication interface.
     *
     * @return the communication interface
     */
    public ICommunication getCommunication() {
        return communication;
    }

    /**
     * Gets the display interface.
     *
     * @return the display interface
     */
    public Display getDisplay() {
        return display;
    }

    /**
     * Executes the main command.
     * This method is required by the Runnable interface but does nothing in this implementation.
     */
    @Override
    public void run() {}
}