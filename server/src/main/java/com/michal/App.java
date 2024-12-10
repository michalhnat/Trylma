package com.michal;

/**
 * The entry point of the application.
 */
public class App {
    /**
     * The main method that starts the server.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}