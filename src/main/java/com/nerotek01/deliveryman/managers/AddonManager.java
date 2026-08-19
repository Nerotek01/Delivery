package com.nerotek01.deliveryman.managers;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.listeners.VotifierListener;
import com.nerotek01.deliveryman.placeholders.MVdWPlaceholders;
import com.nerotek01.deliveryman.placeholders.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;

public class AddonManager {
    private final Main plugin;
    private final Map<String, Boolean> availableAddons = new HashMap<>();
    private final Map<String, Object> registeredHooks = new HashMap<>();

    public AddonManager(Main plugin) {
        this.plugin = plugin;
    }

    public void loadAddons() {
        availableAddons.clear();

        if (checkAddon("Votifier")) {
            try {
                if (!registeredHooks.containsKey("Votifier")) {
                    Bukkit.getPluginManager().registerEvents(new VotifierListener(plugin), plugin);
                    registeredHooks.put("Votifier", Boolean.TRUE);
                }
            } catch (Throwable ex) {
                plugin.getPluginLogger().warning("Failed to load Votifier listener: " + ex.getMessage());
            }
        }

        if (checkAddon("PlaceholderAPI")) {
            try {
                if (!registeredHooks.containsKey("PlaceholderAPI")) {
                    new Placeholders(plugin).register();
                    registeredHooks.put("PlaceholderAPI", Boolean.TRUE);
                }
            } catch (Throwable ex) {
                plugin.getPluginLogger().warning("Failed to register PlaceholderAPI: " + ex.getMessage());
            }
        }

        if (checkAddon("MVdWPlaceholderAPI")) {
            try {
                if (!registeredHooks.containsKey("MVdWPlaceholderAPI")) {
                    new MVdWPlaceholders(plugin).register();
                    registeredHooks.put("MVdWPlaceholderAPI", Boolean.TRUE);
                }
            } catch (Throwable ex) {
                plugin.getPluginLogger().warning("Failed to register MVdWPlaceholderAPI: " + ex.getMessage());
            }
        }
    }

    private boolean checkAddon(String addonName) {
        boolean configEnabled = plugin.getConfig().getBoolean("addons." + addonName, false);
        PluginManager pm = Bukkit.getPluginManager();
        boolean pluginEnabled = pm != null && pm.isPluginEnabled(addonName);

        if (configEnabled && pluginEnabled) {
            plugin.getPluginLogger().info("Hooked into " + addonName + ".");
            availableAddons.put(addonName, true);
            return true;
        }

        if (configEnabled && !pluginEnabled) {
            plugin.getPluginLogger().warning(
                    addonName + " is enabled in config but not installed - disabling config flag.");
            plugin.getConfig().set("addons." + addonName, false);
            plugin.saveConfig();
            plugin.getCm().reload();
        }

        return false;
    }

    public boolean isAvailable(String name) {
        return availableAddons.getOrDefault(name, false);
    }

    public Map<String, Boolean> getAvailableAddons() {
        return availableAddons;
    }
}
