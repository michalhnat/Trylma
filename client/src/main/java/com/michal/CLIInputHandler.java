package com.michal;

import java.util.Scanner;

import com.michal.Commands.ConnectToServerCommand;
import com.michal.Commands.ExitCommand;
import com.michal.Commands.HelpCommand;
import com.michal.Commands.JoinGameCommand;
import com.michal.Commands.ListGamesCommand;
import com.michal.Commands.MoveCommand;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "cli", description = "CLI input handler")
public class CLIInputHandler implements Runnable {
    private CommandLine commandLine;
    private Scanner scanner;
    private ICommunication communication;
    private Display display;

    public CLIInputHandler(ICommunication communication, Display display) {
        this.commandLine = new CommandLine(this);
        this.scanner = new Scanner(System.in);
        this.communication = communication;
        this.display = display;
        commandLine.addSubcommand("connect", new ConnectToServerCommand(communication, display));
        commandLine.addSubcommand("list", new ListGamesCommand(communication, display));
        commandLine.addSubcommand("join", new JoinGameCommand(communication, display));
        commandLine.addSubcommand("move", new MoveCommand(communication, display));
        commandLine.addSubcommand("help", new HelpCommand(communication, display));
        commandLine.addSubcommand("exit", new ExitCommand(communication, display));
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(">");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            try {
                commandLine.execute(input.split(" "));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
