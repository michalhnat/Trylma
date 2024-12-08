package com.michal.Commands;

import java.net.InetAddress;

import com.michal.Display;
import com.michal.ICommunication;
import com.michal.Exceptions.FailedConnectingToServerException;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "connect", description = "Connect to server")
public class ConnectToServerCommand extends AbstractCommand {

    @Parameters(index = "0", description = "Server IP address")
    private String serverIPString;

    @Parameters(index = "1", description = "Server port")
    private int serverPort;

    private Display display;

    public ConnectToServerCommand(ICommunication communication, Display display) {
        super(communication, display);
    }

    @Override
    public void run() {
        if (communication.isConnected()) {
            display.displayMessage("Already connected to a server");
            return;
        }
        try {
            display.displayMessage("Connecting to server " + serverIPString + " on port " + serverPort);
            InetAddress serverIP = InetAddress.getByName(serverIPString);
            communication.connectToServer(serverIP, serverPort);
        } catch (FailedConnectingToServerException e) {
            display.displayError("Failed to connect to server");
        } catch (Exception e) {
            display.displayError("Failed to connect to server");
        }
    }

}
