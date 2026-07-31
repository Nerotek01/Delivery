package com.nerotek01.deliveryman.managers;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.enums.DBType;

public class ConfigManager {
    private final Main plugin;
    private int rewardsRows;
    private int port;
    private String ip;
    private String database;
    private String username;
    private String password;
    private String authSource;
    private DBType dbType;
    private boolean instantUpdate;
    private boolean right;
    private boolean left;
    private boolean useSSL;

    private boolean redisEnabled;
    private String redisHost;
    private int redisPort;
    private String redisPassword;
    private int redisDatabase;
    private int redisTimeout;
    private String redisPrefix;
    private int redisTtl;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        try {
            instantUpdate = plugin.getConfig().getBoolean("rewardsMenu.instantUpdate", true);
            right = plugin.getConfig().getBoolean("interact.right", true);
            left = plugin.getConfig().getBoolean("interact.left", true);
            dbType = DBType.valueOf(plugin.getConfig().getString("database.type", "MONGODB").toUpperCase());
            rewardsRows = Math.max(1, Math.min(6, plugin.getConfig().getInt("rewardsMenu.rows", 5)));
            port = plugin.getConfig().getInt("database.port", 27017);
            ip = plugin.getConfig().getString("database.host", "127.0.0.1");
            database = plugin.getConfig().getString("database.database", "DeliveryMan");
            username = plugin.getConfig().getString("database.username", "");
            password = plugin.getConfig().getString("database.password", "");
            useSSL = plugin.getConfig().getBoolean("database.useSSL", false);
            authSource = plugin.getConfig().getString("database.authSource", "admin");

            redisEnabled = plugin.getConfig().getBoolean("redis.enabled", false);
            redisHost = plugin.getConfig().getString("redis.host", "127.0.0.1");
            redisPort = plugin.getConfig().getInt("redis.port", 6379);
            redisPassword = plugin.getConfig().getString("redis.password", "");
            redisDatabase = plugin.getConfig().getInt("redis.database", 0);
            redisTimeout = plugin.getConfig().getInt("redis.timeout", 5000);
            redisPrefix = plugin.getConfig().getString("redis.prefix", "deliveryman:");
            redisTtl = plugin.getConfig().getInt("redis.ttl", 3600);
        } catch (IllegalArgumentException ex) {
            plugin.getLogger().severe("Invalid config value: " + ex.getMessage());
            throw ex;
        }
    }

    public int getRewardsRows() { return rewardsRows; }
    public int getPort() { return port; }
    public String getIp() { return ip; }
    public String getDatabase() { return database; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getAuthSource() { return authSource; }
    public DBType getDbType() { return dbType; }
    public boolean isInstantUpdate() { return instantUpdate; }
    public boolean isRight() { return right; }
    public boolean isLeft() { return left; }
    public boolean isUseSSL() { return useSSL; }

    public boolean isRedisEnabled() { return redisEnabled; }
    public String getRedisHost() { return redisHost; }
    public int getRedisPort() { return redisPort; }
    public String getRedisPassword() { return redisPassword; }
    public int getRedisDatabase() { return redisDatabase; }
    public int getRedisTimeout() { return redisTimeout; }
    public String getRedisPrefix() { return redisPrefix; }
    public int getRedisTtl() { return redisTtl; }
}
