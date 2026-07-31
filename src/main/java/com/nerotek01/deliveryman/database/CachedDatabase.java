package com.nerotek01.deliveryman.database;

import com.google.gson.Gson;
import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.api.DeliveryPlayerLoadEvent;
import com.nerotek01.deliveryman.cache.RedisCache;
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.interfaces.Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CachedDatabase implements Database {

    private static final String KEY_PREFIX = "player:";
    private final Main plugin;
    private final Database delegate;
    private final RedisCache redis;
    private final Gson gson;

    public CachedDatabase(Main plugin, Database delegate, RedisCache redis) {
        this.plugin = plugin;
        this.delegate = delegate;
        this.redis = redis;
        this.gson = plugin.getGson();
    }

    private String cacheKey(Player p) {
        return KEY_PREFIX + p.getUniqueId();
    }

    @Override
    public void loadPlayer(Player p) {
        if (redis.isEnabled()) {
            String json = redis.get(cacheKey(p));
            if (json != null) {
                try {
                    PlayerData pd = gson.fromJson(json, PlayerData.class);
                    if (pd != null) {
                        plugin.getDm().addPlayer(p, pd);
                        fireLoadEvent(p);
                        plugin.getPluginLogger().debug("Cache HIT for player " + p.getName());
                        return;
                    }
                } catch (Exception ex) {
                    plugin.getPluginLogger().debug("Cache deserialize failed for " + p.getName() + ": " + ex.getMessage());
                }
            }
            plugin.getPluginLogger().debug("Cache MISS for player " + p.getName());
        }
        delegate.loadPlayer(p);
    }

    public void onBackendLoaded(Player p, PlayerData pd) {
        if (redis.isEnabled() && pd != null) {
            try {
                redis.set(cacheKey(p), gson.toJson(pd));
            } catch (Exception ignored) {
            }
        }
        fireLoadEvent(p);
    }

    @Override
    public void savePlayer(Player p) {
        PlayerData pd = plugin.getDm().getPlayerData(p);
        if (pd != null && redis.isEnabled()) {
            try {
                redis.set(cacheKey(p), gson.toJson(pd));
            } catch (Exception ignored) {
            }
        }
        delegate.savePlayer(p);
    }

    @Override
    public void savePlayerSync(Player p) {
        PlayerData pd = plugin.getDm().getPlayerData(p);
        if (pd != null && redis.isEnabled()) {
            try {
                redis.set(cacheKey(p), gson.toJson(pd));
            } catch (Exception ignored) {
            }
        }
        delegate.savePlayerSync(p);
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public String backendName() {
        return delegate.backendName() + "+Redis";
    }

    private void fireLoadEvent(Player p) {
        Bukkit.getScheduler().runTask(plugin, () ->
                Bukkit.getPluginManager().callEvent(new DeliveryPlayerLoadEvent(p)));
    }
}
