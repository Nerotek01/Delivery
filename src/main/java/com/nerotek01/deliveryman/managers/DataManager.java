package com.nerotek01.deliveryman.managers;

import com.nerotek01.deliveryman.data.PlayerData;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DataManager {
    private final Map<UUID, PlayerData> players = new ConcurrentHashMap<>();
    private final Map<UUID, Boolean> saving = new ConcurrentHashMap<>();

    public void addPlayer(Player p, PlayerData pd) {
        players.put(p.getUniqueId(), pd);
    }

    public void addPlayer(UUID uuid, PlayerData pd) {
        players.put(uuid, pd);
    }

    public PlayerData getPlayerData(Player p) {
        return players.get(p.getUniqueId());
    }

    public PlayerData getPlayerData(UUID uuid) {
        return players.get(uuid);
    }

    public PlayerData getOrCreatePlayerData(Player p) {
        return players.computeIfAbsent(p.getUniqueId(), PlayerData::new);
    }

    public boolean markSaving(UUID uuid) {
        return saving.putIfAbsent(uuid, Boolean.TRUE) == null;
    }

    public void unmarkSaving(UUID uuid) {
        saving.remove(uuid);
    }

    public boolean isSaving(UUID uuid) {
        return saving.getOrDefault(uuid, Boolean.FALSE);
    }

    public void removePlayer(Player p) {
        players.remove(p.getUniqueId());
    }

    public void removePlayer(UUID uuid) {
        players.remove(uuid);
    }

    public void clearAll() {
        players.clear();
        saving.clear();
    }
}
