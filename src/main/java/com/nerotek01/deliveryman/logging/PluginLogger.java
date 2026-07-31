package com.nerotek01.deliveryman.logging;

import com.nerotek01.deliveryman.Main;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class PluginLogger {

    private final Main plugin;
    private final Logger root;
    private LogLevel level = LogLevel.INFO;
    private boolean debug = false;

    public PluginLogger(Main plugin) {
        this.plugin = plugin;
        this.root = plugin.getLogger();
    }

    public void setLevel(LogLevel level) {
        this.level = level == null ? LogLevel.INFO : level;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebug() {
        return debug;
    }

    public void info(String message) {
        if (!level.allows(Level.INFO)) return;
        root.info(message);
    }

    public void success(String message) {
        if (!level.allows(Level.INFO)) return;
        root.info(message);
    }

    public void warning(String message) {
        if (!level.allows(Level.WARNING)) return;
        root.warning(message);
    }

    public void warning(String message, Throwable throwable) {
        if (!level.allows(Level.WARNING)) return;
        root.log(Level.WARNING, message, throwable);
    }

    public void severe(String message) {
        if (!level.allows(Level.SEVERE)) return;
        root.severe(message);
    }

    public void severe(String message, Throwable throwable) {
        if (!level.allows(Level.SEVERE)) return;
        root.log(Level.SEVERE, message, throwable);
    }

    public void debug(String message) {
        if (!debug) return;
        root.info("[DEBUG] " + message);
    }
}
