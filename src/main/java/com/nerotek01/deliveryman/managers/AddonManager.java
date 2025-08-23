package com.nerotek01.deliveryman.managers;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.addons.*;
import com.nerotek01.deliveryman.placeholders.MVdWPlaceholders;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AddonManager {
    private final Main plugin;
    private final Map<String, Boolean> availableAddons = new HashMap<>();
    private HologramAddon hologramAddon;
    private ServerNPCAddon npcAddon;

    public AddonManager(Main plugin) {
        this.plugin = plugin;
    }

    public void loadAddons() {
        checkAndRegisterAddon("CMI", () -> new CMIAddon(), "hologramAddon");
        checkAndRegisterAddon("HolographicDisplays", () -> new HolographicDisplaysAddon(), "hologramAddon");
        checkAndRegisterAddon("Holograms", () -> new HologramsAddon(), "hologramAddon");
        checkAndRegisterAddon("TrHologram", () -> new TrHologramAddon(), "hologramAddon");

        if (checkAddon("ServerNPC")) {
            this.npcAddon = new ServerNPCAddon(plugin);
            registerListener(npcAddon);
        }

        if (checkAddon("Votifier")) {
            registerListener(new VotifierListener(plugin));
        }

        if (checkAddon("PlaceholderAPI")) {
            new Placeholders(plugin).register();
        }

        if (checkAddon("MVdWPlaceholderAPI")) {
            new MVdWPlaceholders(plugin).register();
        }
    }

    private void checkAndRegisterAddon(String addonName, Supplier<HologramAddon> supplier, String type) {
        if (checkAddon(addonName)) {
            if ("hologramAddon".equals(type)) {
                this.hologramAddon = supplier.get();
            }
        }
    }

    private boolean checkAddon(String addonName) {
        boolean configEnabled = plugin.getConfig().getBoolean("addons." + addonName, false);
        boolean pluginEnabled = Bukkit.getPluginManager().isPluginEnabled(addonName);

        if (configEnabled && pluginEnabled) {
            plugin.sendLogMessage("§aHooked into §8" + addonName + "§a successfully!");
            availableAddons.put(addonName, true);
            return true;
        }

        if (configEnabled) {
            plugin.getConfig().set("addons." + addonName, false);
            plugin.saveConfig();
            plugin.getCm().reload();
        }

        return false;
    }

    private void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    public boolean isHologramAddonEnabled() {
        return hologramAddon != null;
    }

    public boolean isNPCAddonEnabled() {
        return npcAddon != null;
    }

    public HologramAddon getHologramAddon() {
        return hologramAddon;
    }

    public ServerNPCAddon getNpcAddon() {
        return npcAddon;
    }
}