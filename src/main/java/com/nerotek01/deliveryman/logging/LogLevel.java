package com.nerotek01.deliveryman.logging;

import java.util.logging.Level;

public enum LogLevel {
    NONE(Level.OFF),
    SEVERE(Level.SEVERE),
    WARNING(Level.WARNING),
    INFO(Level.INFO),
    FINE(Level.FINE),
    ALL(Level.ALL);

    private final Level backend;

    LogLevel(Level backend) {
        this.backend = backend;
    }

    public boolean allows(Level messageLevel) {
        return messageLevel.intValue() >= backend.intValue();
    }

    public static LogLevel fromString(String value, LogLevel def) {
        if (value == null) return def;
        try {
            return LogLevel.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return def;
        }
    }
}
