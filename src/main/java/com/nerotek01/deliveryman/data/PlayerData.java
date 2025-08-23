package com.nerotek01.deliveryman.data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerData {
    private final UUID uuid;
    private final Map<String, Long> claimed;
    private final Map<String, Integer> streaks;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.claimed = new ConcurrentHashMap<>();
        this.streaks = new ConcurrentHashMap<>();
    }

    // Getters
    public UUID getUuid() {
        return uuid;
    }

    public Map<String, Integer> getStreaks() {
        return Collections.unmodifiableMap(streaks);
    }

    public Map<String, Long> getClaimed() {
        return Collections.unmodifiableMap(claimed);
    }

    // Setters with thread-safe operations
    public void setStreak(String key, int value) {
        streaks.put(key, value);
    }

    public void setClaim(String key, long value) {
        claimed.put(key, value);
    }

    // Bulk operations
    public void setAllStreaks(Map<String, Integer> newStreaks) {
        streaks.clear();
        streaks.putAll(newStreaks);
    }

    public void setAllClaims(Map<String, Long> newClaims) {
        claimed.clear();
        claimed.putAll(newClaims);
    }

    // Utility methods
    public boolean hasClaimed(String key) {
        return claimed.containsKey(key);
    }

    public int getStreakCount(String key) {
        return streaks.getOrDefault(key, 0);
    }

    public long getClaimTime(String key) {
        return claimed.getOrDefault(key, 0L);
    }
}