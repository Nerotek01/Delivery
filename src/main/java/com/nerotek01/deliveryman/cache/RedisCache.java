package com.nerotek01.deliveryman.cache;

import com.nerotek01.deliveryman.Main;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.time.Duration;

public class RedisCache {

    private final Main plugin;
    private volatile JedisPool pool;
    private volatile String prefix;
    private volatile int ttlSeconds;
    private volatile boolean enabled;

    public RedisCache(Main plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        this.prefix = plugin.getConfig().getString("redis.prefix", "delivery:");
        this.ttlSeconds = plugin.getConfig().getInt("redis.ttl", 3600);
        this.enabled = plugin.getConfig().getBoolean("redis.enabled", false);
    }

    public void reload() {
        boolean wasEnabled = enabled;
        loadConfig();
        if (enabled && !wasEnabled) {
            connect();
        } else if (!enabled && wasEnabled) {
            close();
        } else if (enabled && wasEnabled) {
            close();
            connect();
        }
    }

    public boolean isEnabled() {
        return enabled && pool != null && !pool.isClosed();
    }

    public void connect() {
        if (!enabled) {
            return;
        }
        try {
            String host = plugin.getConfig().getString("redis.host", "127.0.0.1");
            int port = plugin.getConfig().getInt("redis.port", 6379);
            String password = plugin.getConfig().getString("redis.password", "");
            int database = plugin.getConfig().getInt("redis.database", 0);
            int timeout = plugin.getConfig().getInt("redis.timeout", 5000);

            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(16);
            config.setMaxIdle(8);
            config.setMinIdle(2);
            config.setTestOnBorrow(true);
            config.setTestWhileIdle(true);
            config.setBlockWhenExhausted(true);
            config.setMaxWait(Duration.ofMillis(2000));

            if (password != null && !password.isEmpty()) {
                pool = new JedisPool(config, host, port, timeout, password, database);
            } else {
                pool = new JedisPool(config, host, port, timeout, null, database);
            }

            try (Jedis jedis = pool.getResource()) {
                jedis.ping();
            }
            plugin.getLogger().info("Redis cache connected.");
        } catch (JedisConnectionException ex) {
            plugin.getLogger().warning("Redis connection failed - caching disabled. Cause: " + ex.getMessage());
            close();
        } catch (Exception ex) {
            plugin.getLogger().warning("Redis initialization error - caching disabled. Cause: " + ex.getMessage());
            close();
        }
    }

    public void close() {
        JedisPool current = pool;
        if (current != null && !current.isClosed()) {
            try {
                current.close();
            } catch (Exception ignored) {
            }
        }
        pool = null;
    }

    public void set(String key, String value) {
        if (!isEnabled() || key == null || value == null) return;
        String full = prefix + key;
        try (Jedis jedis = pool.getResource()) {
            if (ttlSeconds > 0) {
                jedis.setex(full, ttlSeconds, value);
            } else {
                jedis.set(full, value);
            }
        } catch (Exception ex) {
            plugin.getLogger().warning("Redis SET failed for key " + key + ": " + ex.getMessage());
        }
    }

    public String get(String key) {
        if (!isEnabled()) return null;
        String full = prefix + key;
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(full);
        } catch (Exception ex) {
            plugin.getLogger().warning("Redis GET failed for key " + key + ": " + ex.getMessage());
            return null;
        }
    }
}
