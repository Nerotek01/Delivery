package com.nerotek01.deliveryman.config;

import com.nerotek01.deliveryman.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
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
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        this.config = YamlConfiguration.loadConfiguration(file);

        try {
            InputStream defaultConfig = plugin.getResource(fileName + ".yml");
            if (defaultConfig == null) return;

            YamlConfiguration defaultYaml = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultConfig, StandardCharsets.UTF_8));

            if (!file.exists() || defaults) {
                config.addDefaults(defaultYaml);
                config.options().copyDefaults(true);
                save();
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load config: " + fileName);
            plugin.getLogger().severe(e.toString());
        }
    }

    public void reload() {
        try {
            config.load(file);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to reload config: " + file.getName());
            plugin.getLogger().severe(e.toString());
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
            plugin.getLogger().severe("Failed to save config: " + file.getName());
            plugin.getLogger().severe(e.toString());
        }
    }

    public Object getObject(String path) {
        return config.get(path);
    }

    public String getString(String path, String def) {
        return config.getString(path, def);
    }

    public String get(String path) {
        String value = config.getString(path);
        return value == null ? null : value.replace("&", "\u00a7");
    }

    public String getOrDefault(String path, String def) {
        if (!config.isSet(path)) {
            config.set(path, def);
            save();
            return def == null ? null : def.replace("&", "\u00a7");
        }
        return get(path);
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public int getInt(String path, int def) {
        return config.getInt(path, def);
    }

    public int getIntOrDefault(String path, int def) {
        if (!config.isSet(path)) {
            config.set(path, def);
            save();
            return def;
        }
        return config.getInt(path);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public boolean getBooleanOrDefault(String path, boolean def) {
        if (!config.isSet(path)) {
            config.set(path, def);
            save();
            return def;
        }
        return config.getBoolean(path);
    }

    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public List<String> getList(String path) {
        List<String> result = new ArrayList<>();
        List<?> raw = config.getList(path);
        if (raw == null) return result;
        for (Object o : raw) {
            if (o != null) result.add(String.valueOf(o).replace("&", "\u00a7"));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getListOrDefault(String path, List<T> def) {
        if (!config.isSet(path)) {
            config.set(path, def);
            save();
            return def;
        }
        List<?> raw = config.getList(path);
        if (raw == null) return def;
        List<T> result = new ArrayList<>();
        for (Object o : raw) {
            try {
                result.add((T) o);
            } catch (ClassCastException ignored) {
            }
        }
        return result.isEmpty() ? def : result;
    }

    public List<String> getListOrDefaultStrings(String path, List<String> def) {
        if (!config.isSet(path)) {
            config.set(path, def);
            save();
            return def;
        }
        List<String> raw = config.getStringList(path);
        return raw.isEmpty() ? def : raw;
    }

    public boolean isSet(String path) {
        return config.isSet(path);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }

    public List<String> getListOrEmpty(String path) {
        List<String> r = getList(path);
        return r == null ? Collections.emptyList() : r;
    }
}
