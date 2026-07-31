package com.nerotek01.deliveryman.cache;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.logging.PluginLogger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.time.Duration;

public class RedisCache {

    private final Main plugin;
    private final PluginLogger logger;
    private JedisPool pool;
    private final String prefix;
    private final int ttlSeconds;
    private final boolean enabled;

    public RedisCache(Main plugin) {
        this.plugin = plugin;
        this.logger = plugin.getPluginLogger();
        this.prefix = plugin.getConfig().getString("redis.prefix", "deliveryman:");
        this.ttlSeconds = plugin.getConfig().getInt("redis.ttl", 3600);
        this.enabled = plugin.getConfig().getBoolean("redis.enabled", false);
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
        } catch (JedisConnectionException ex) {
            logger.warning("Redis connection failed - caching disabled. Cause: " + ex.getMessage());
            close();
        } catch (Exception ex) {
            logger.warning("Redis initialization error - caching disabled. Cause: " + ex.getMessage());
            close();
        }
    }

    public void close() {
        if (pool != null && !pool.isClosed()) {
            try {
                pool.close();
            } catch (Exception ignored) {
            }
        }
        pool = null;
    }

    public String get(String key) {
        if (!isEnabled()) return null;
        String full = prefix + key;
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(full);
        } catch (Exception ex) {
            logger.debug("Redis GET failed for key " + key + ": " + ex.getMessage());
            return null;
        }
    }

    public void set(String key, String value) {
        set(key, value, ttlSeconds);
    }

    public void set(String key, String value, int ttlOverride) {
        if (!isEnabled()) return;
        String full = prefix + key;
        try (Jedis jedis = pool.getResource()) {
            if (ttlOverride > 0) {
                jedis.setex(full, ttlOverride, value);
            } else {
                jedis.set(full, value);
            }
        } catch (Exception ex) {
            logger.debug("Redis SET failed for key " + key + ": " + ex.getMessage());
        }
    }

    public void del(String key) {
        if (!isEnabled()) return;
        String full = prefix + key;
        try (Jedis jedis = pool.getResource()) {
            jedis.del(full);
        } catch (Exception ex) {
            logger.debug("Redis DEL failed for key " + key + ": " + ex.getMessage());
        }
    }

    public boolean exists(String key) {
        if (!isEnabled()) return false;
        String full = prefix + key;
        try (Jedis jedis = pool.getResource()) {
            return jedis.exists(full);
        } catch (Exception ex) {
            logger.debug("Redis EXISTS failed for key " + key + ": " + ex.getMessage());
            return false;
        }
    }

    public int getTtlSeconds() {
        return ttlSeconds;
    }

    public String getPrefix() {
        return prefix;
    }
}
