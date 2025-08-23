package com.nerotek01.deliveryman.controllers;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.nms.NPC_v1_8_R3;
import com.nerotek01.deliveryman.nms.packets.PacketReader;
import com.nerotek01.deliveryman.skins.SkinProperty;
import com.nerotek01.deliveryman.superclass.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class VersionController {
    private final Main plugin;
    private final PacketReader reader;

    public VersionController(Main plugin) {
        this.plugin = plugin;
        this.reader = new PacketReader();
        checkVersion();
    }

    private void checkVersion() {
        String version;
        try {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException ex) {
            plugin.getLogger().severe("Failed to detect server version!");
            disable();
            return;
        }

        if (!version.equals("v1_8_R3")) {
            plugin.getLogger().severe("This version only supports 1.8.8 (v1_8_R3)!");
            disable();
        }
    }

    public void disable() {
        Bukkit.getScheduler().cancelTasks(plugin);
        Bukkit.getPluginManager().disablePlugin(plugin);
    }

    public NPC getNPC(Location location, SkinProperty sp) {
        return new NPC_v1_8_R3(location, sp);
    }

    public PacketReader getReader() {
        return reader;
    }
}