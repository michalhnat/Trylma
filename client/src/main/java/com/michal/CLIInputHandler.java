package com.michal;

import java.io.ObjectOutputStream;
import java.util.Scanner;

import com.michal.Commands.ConnectToServerCommand;
import com.michal.Commands.JoinGameCommand;
import com.michal.Commands.ListGamesCommand;
import com.michal.Commands.MoveCommand;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "cli", description = "CLI input handler")
public class CLIInputHandler implements Runnable {
    private CommandLine commandLine;
    private Scanner scanner;
    private ObjectOutputStream out;

    public CLIInputHandler(ICommunication communication) {
        commandLine = new CommandLine(this);
        scanner = new Scanner(System.in);
        commandLine.addSubcommand("connect", new ConnectToServerCommand(communication));
        commandLine.addSubcommand("list", new ListGamesCommand(communication));
        commandLine.addSubcommand("join", new JoinGameCommand(communication));
        commandLine.addSubcommand("move", new MoveCommand(communication));
    }

    @Override
    public void run() {
        System.out.println("Dummy text");
        while (true) {
            System.out.print(">");
            String input = scanner.nextLine().trim();

            if (input.equals("exit")) {
                System.out.println("Exiting...");
                System.exit(0);
            }

            try {
                commandLine.execute(input.split(" "));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
