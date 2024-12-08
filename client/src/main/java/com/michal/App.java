package com.michal;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        ICommunication communication = new SocketCommunication();
        Display display = new ConsoleDisplay();
        CLIInputHandler inputHandler = new CLIInputHandler(communication, display);
        ServerMessageHandler messageHandler = new MessageReceiver();
        Client client = new Client(communication, display, inputHandler, messageHandler);

        client.run();
    }
}
