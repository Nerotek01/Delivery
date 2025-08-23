package com.nerotek01.deliveryman.superclass;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.skins.SkinProperty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class NPC {

    protected final Location location;
    protected final SkinProperty skinProperty;

    protected final Set<UUID> viewers = new HashSet<>();
    protected final Set<UUID> sent = new HashSet<>();

    public NPC(Location location, SkinProperty skinProperty) {
        this.location = location;
        this.skinProperty = skinProperty;
    }

    public void remove(Player player) {
        UUID id = player.getUniqueId();
        sent.remove(id);
        if (viewers.remove(id)) {
            hide(player);
        }
    }

    public void check(Player player) {
        Location ploc = player.getLocation();
        if (!ploc.getWorld().equals(location.getWorld())) return;

        UUID id = player.getUniqueId();
        if (Main.get().getNpc().check(player, location)) {
            if (!viewers.contains(id)) {
                spawn(player);
                viewers.add(id);
            }
        } else if (ploc.distance(location) >= 40.0D && viewers.remove(id)) {
            hide(player);
        }
    }

    public void removeAll() {
        for (UUID id : new HashSet<>(viewers)) {
            Player p = Bukkit.getPlayer(id);
            if (p != null) remove(p);
        }
    }

    public abstract int getEntityID();
    public abstract void spawn(Player player);
    public abstract void hide(Player player);
}
