package com.michal.Utils;

import java.io.IOException;
import java.util.logging.*;

/**
 * A utility class for configuring the global logger.
 */
public class MyLogger {
    /**
     * Private constructor to prevent instantiation.
     *
     * @throws InstantiationError always thrown to indicate that this class should not be instantiated
     */
    private MyLogger() {
        throw new InstantiationError("MyLogger is a full static class");
    }

    /**
     * The global logger instance.
     */
    public static final Logger logger = Logger.getGlobal();

    /**
     * Configures the global logger by removing existing handlers, adding a console handler, and a file handler.
     * The console handler logs messages at the INFO level, while the file handler logs all messages to a file named "log.txt".
     */
    public static void loggerConfig() {
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers)
            logger.removeHandler(handler);

        logger.setUseParentHandlers(false);

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.INFO);
        logger.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("./log.txt");
            fh.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException | SecurityException e) {
            // Handle exceptions related to file handling and security
        }

        logger.setLevel(Level.ALL);
    }
}