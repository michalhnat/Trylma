package com.michal;

/**
 * The entry point of the application.
 */
public class App {
    /**
     * The main method that initializes and runs the client.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        ICommunication communication = new SocketCommunication();
        Display display = new ConsoleDisplay();
        CLIInputHandler inputHandler = new CLIInputHandler(communication, display);
        ServerMessageHandler messageHandler = new MessageReceiver();
        Client client = new Client(communication, display, inputHandler, messageHandler);

        client.run();
    }
}