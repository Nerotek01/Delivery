package com.nerotek01.deliveryman.managers;

import com.nerotek01.deliveryman.data.PlayerData;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DataManager {
    private final Map<UUID, PlayerData> players = new ConcurrentHashMap<>();

    public void addPlayer(UUID uuid, PlayerData pd) {
        players.put(uuid, pd);
    }

    public PlayerData getPlayerData(UUID uuid) {
        return players.get(uuid);
    }

    public PlayerData getOrCreatePlayerData(UUID uuid) {
        return players.computeIfAbsent(uuid, PlayerData::new);
    }

    public void removePlayer(UUID uuid) {
        players.remove(uuid);
    }

    public void mergePlayer(UUID uuid, PlayerData loaded) {
        players.compute(uuid, (key, existing) -> {
            if (existing == null) return loaded;
            for (Map.Entry<String, Long> entry : loaded.getClaimed().entrySet()) {
                if (entry.getValue() > existing.getClaimTime(entry.getKey())) {
                    existing.setClaim(entry.getKey(), entry.getValue());
                }
            }
            for (Map.Entry<String, Integer> entry : loaded.getStreaks().entrySet()) {
                if (entry.getValue() > existing.getStreakCount(entry.getKey())) {
                    existing.setStreak(entry.getKey(), entry.getValue());
                }
            }
            return existing;
        });
    }

    public void clearAll() {
        players.clear();
    }
}
