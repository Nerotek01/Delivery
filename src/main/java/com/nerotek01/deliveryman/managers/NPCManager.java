package com.nerotek01.deliveryman.managers;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.npc.NPCData;
import com.nerotek01.deliveryman.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import java.util.*;

public class NPCManager {
    private final Main plugin;
    private final Map<Integer, NPCData> npcs = new HashMap<>();

    public NPCManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        npcs.values().forEach(NPCData::delete);
        npcs.clear();

        if (plugin.getConfig().isSet("npcs")) {
            plugin.getConfig().getConfigurationSection("npcs").getKeys(false).forEach(s -> {
                Location loc = Utils.getStringLocation(plugin.getConfig().getString("npcs." + s));
                NPCData data = new NPCData(plugin, loc);
                npcs.put(data.getID(), data);
            });
        }
    }

    public void check(Player p) {
        if (!plugin.getAdm().isNPCAddon()) {
            npcs.values().forEach(n -> n.getNpc().check(p));
        }
    }

    public void remove(Player p) {
        if (!plugin.getAdm().isNPCAddon()) {
            npcs.values().forEach(n -> n.getNpc().remove(p));
        }
    }

    public boolean check(Player p, Location npcLoc) {
        double distanceSquared = p.getLocation().distanceSquared(npcLoc);
        int viewRange = Bukkit.getViewDistance() << 4;
        return distanceSquared <= 900 && distanceSquared <= viewRange * viewRange
                && isLookingAt(p, npcLoc);
    }

    private boolean isLookingAt(Player p, Location npcLoc) {
        Vector toNpc = npcLoc.toVector().subtract(p.getLocation().toVector());
        return toNpc.normalize().dot(p.getLocation().getDirection()) >= 0.95D;
    }

    public Map<Integer, NPCData> getNpcs() {
        return npcs;
    }
}