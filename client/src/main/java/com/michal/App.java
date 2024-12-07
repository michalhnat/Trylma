package com.michal;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        ICommunication communication = new SocketCommuncation();
        Display display = new ConsoleDisplay();

        CLIInputHandler inputHandler = new CLIInputHandler(communication);

        Client client = new Client(communication, display, inputHandler);

        client.run();
    }
}
