package com.nerotek01.deliveryman.managers;

import com.nerotek01.deliveryman.data.PlayerData;
import org.bukkit.entity.Player;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataManager {
    private final Map<UUID, PlayerData> players = new ConcurrentHashMap<>();
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

    public void addPlayer(Player p, PlayerData pd) {
        players.put(p.getUniqueId(), pd);
    }

    public PlayerData getPlayerData(Player p) {
        return players.computeIfAbsent(p.getUniqueId(), PlayerData::new);
    }

    public void removePlayer(Player p) {
        players.remove(p.getUniqueId());
    }
}