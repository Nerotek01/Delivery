package com.nerotek01.deliveryman.managers;

import com.nerotek01.deliveryman.data.PlayerData;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DataManager {
    private final Map<UUID, PlayerData> players = new ConcurrentHashMap<>();
    private final Map<UUID, Boolean> saving = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> voting = new ConcurrentHashMap<>();

    public void setVoting(String name, String vote) {
        voting.computeIfAbsent(name, k -> ConcurrentHashMap.newKeySet()).add(vote);
    }

    public void removeVoting(String name, String vote) {
        Optional.ofNullable(voting.get(name)).ifPresent(set -> set.remove(vote));
    }

    public boolean isVoting(String name, String vote) {
        return Optional.ofNullable(voting.get(name))
                .map(set -> set.contains(vote))
                .orElse(false);
    }

    public void clearVoting(String name) {
        voting.remove(name);
    }

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
        voting.clear();
    }
}
