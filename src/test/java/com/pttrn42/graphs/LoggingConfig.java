package com.pttrn42.graphs;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingConfig {

    public LoggingConfig() {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "(%2$s) %5$s %6$s%n");

        final ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINEST);
        consoleHandler.setFormatter(new SimpleFormatter());

        final Logger app = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        app.setLevel(Level.FINEST);
        app.addHandler(consoleHandler);
    }

}
