package com.michal.Commands;

import java.io.ObjectOutputStream;
import java.net.InetAddress;

import com.michal.ICommunication;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "connect", description = "Connect to server")
public class ConnectToServerCommand extends AbstractCommand {

    @Parameters(index = "0", description = "Server IP address")
    private String serverIPString;

    @Parameters(index = "1", description = "Server port")
    private int serverPort;

    public ConnectToServerCommand(ICommunication communication) {
        super(communication);
    }

    @Override
    public void run() {
        if (communication.isConnected()) {
            System.out.println("Already connected to a server");
            return;
        }
        try {
            System.out.println("Connecting to server " + serverIPString + " on port " + serverPort);
            InetAddress serverIP = InetAddress.getByName(serverIPString);
            communication.connectToServer(serverIP, serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
