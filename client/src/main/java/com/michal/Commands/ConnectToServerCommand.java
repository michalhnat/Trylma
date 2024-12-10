package com.michal.Commands;

import java.net.InetAddress;
import com.michal.*;
import picocli.CommandLine.*;

/**
 * Command to connect to a server.
 * Implements the Runnable interface to be executed as a command.
 */
@Command(name = "connect", description = "Connect to server")
public class ConnectToServerCommand implements Runnable {

    @ParentCommand
    private MainCommand parent;

    @Parameters(index = "0", description = "Server IP address")
    private String serverIPString;

    @Parameters(index = "1", description = "Server port")
    private int serverPort;

    /**
     * Executes the command to connect to the server.
     * Displays messages based on the connection status.
     */
    @Override
    public void run() {
        Display display = parent.getDisplay();
        ICommunication communication = parent.getCommunication();

        if (communication.isConnected()) {
            display.displayMessage("Already connected to a server");
            return;
        }
        try {
            display.displayMessage(
                    "Connecting to server " + serverIPString + " on port " + serverPort);
            InetAddress serverIP = InetAddress.getByName(serverIPString);
            communication.connectToServer(serverIP, serverPort);
        } catch (Exception e) {
            display.displayError("Failed to connect to server");
        }
    }
}