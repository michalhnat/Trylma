package com.michal.Utils;

import java.io.IOException;
import java.util.logging.*;

/**
 * Utility class for configuring and managing the global logger.
 * This class cannot be instantiated.
 */
public class MyLogger {
    /**
     * Private constructor to prevent instantiation.
     * Throws an InstantiationError if called.
     */
    private MyLogger() {
        throw new InstantiationError("MyLogger is a full static class");
    }

    /**
     * The global logger instance.
     */
    public static final Logger logger = Logger.getGlobal();

    /**
     * Configures the global logger by removing existing handlers,
     * setting the logger to not use parent handlers, and adding
     * a console handler and a file handler.
     */
    public static void loggerConfig() {
        // Remove existing handlers
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers)
            logger.removeHandler(handler);

        // Disable use of parent handlers
        logger.setUseParentHandlers(false);

        // Add console handler
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.INFO);
        logger.addHandler(ch);

        // Add file handler
        try {
            FileHandler fh = new FileHandler("./log.txt");
            fh.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException | SecurityException e) {
            // Handle exceptions silently
        }

        // Set logger level to ALL
        logger.setLevel(Level.ALL);
    }
}