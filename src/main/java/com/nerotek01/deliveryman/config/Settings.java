package com.nerotek01.deliveryman.config;

import com.nerotek01.deliveryman.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Settings {
    private final Main plugin;
    private final File file;
    private final YamlConfiguration config;
    private final boolean keepComments;

    public Settings(Main plugin, String fileName, boolean defaults, boolean keepComments) {
        this.plugin = plugin;
        this.keepComments = keepComments;
        this.file = new File(plugin.getDataFolder(), fileName + ".yml");
        this.config = YamlConfiguration.loadConfiguration(file);

        try {
            InputStream defaultConfig = plugin.getResource(fileName + ".yml");
            if (defaultConfig == null) return;

            YamlConfiguration defaultYaml = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultConfig, StandardCharsets.UTF_8));

            if (!file.exists()) {
                config.addDefaults(defaultYaml);
                config.options().copyDefaults(true);
                save();
            } else if (defaults) {
                config.addDefaults(defaultYaml);
                config.options().copyDefaults(true);
                save();
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load config: " + fileName);
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            config.load(file);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to reload config");
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            if (keepComments) {
                String data = config.saveToString();
                try (BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
                    writer.write(data);
                }
            } else {
                config.save(file);
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save config");
            e.printStackTrace();
        }
    }

    // Helper methods
    public String getString(String path) {
        return config.getString(path, "").replace("&", "§");
    }

    public String getStringOrDefault(String path, String def) {
        if (!config.isSet(path)) {
            config.set(path, def);
            save();
            return def;
        }
        return getString(path);
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}