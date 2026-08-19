package com.nerotek01.deliveryman.managers;

import com.nerotek01.deliveryman.Main;

public class ConfigManager {
    private final Main plugin;
    private boolean menuEnabled;
    private int rewardsRows;

    private String dbHost;
    private int dbPort;
    private String dbDatabase;
    private String dbUsername;
    private String dbPassword;
    private boolean dbUseSSL;
    private String dbAuthSource;

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
        boolean newMenuEnabled = plugin.getConfig().getBoolean("rewardsmenu.enabled", true);
        int newRewardsRows = Math.max(5, Math.min(6, plugin.getConfig().getInt("rewardsmenu.rows", 6)));

        String newDbHost = plugin.getConfig().getString("database.host", "127.0.0.1");
        int newDbPort = plugin.getConfig().getInt("database.port", 27017);
        String newDbDatabase = plugin.getConfig().getString("database.database", "Delivery");
        String newDbUsername = plugin.getConfig().getString("database.username", "");
        String newDbPassword = plugin.getConfig().getString("database.password", "");
        boolean newDbUseSSL = plugin.getConfig().getBoolean("database.useSSL", false);
        String newDbAuthSource = plugin.getConfig().getString("database.authSource", "admin");

        boolean newRedisEnabled = plugin.getConfig().getBoolean("redis.enabled", false);
        String newRedisHost = plugin.getConfig().getString("redis.host", "127.0.0.1");
        int newRedisPort = plugin.getConfig().getInt("redis.port", 6379);
        String newRedisPassword = plugin.getConfig().getString("redis.password", "");
        int newRedisDatabase = plugin.getConfig().getInt("redis.database", 0);
        int newRedisTimeout = plugin.getConfig().getInt("redis.timeout", 5000);
        String newRedisPrefix = plugin.getConfig().getString("redis.prefix", "delivery:");
        int newRedisTtl = plugin.getConfig().getInt("redis.ttl", 3600);

        this.menuEnabled = newMenuEnabled;
        this.rewardsRows = newRewardsRows;

        this.dbHost = newDbHost;
        this.dbPort = newDbPort;
        this.dbDatabase = newDbDatabase;
        this.dbUsername = newDbUsername;
        this.dbPassword = newDbPassword;
        this.dbUseSSL = newDbUseSSL;
        this.dbAuthSource = newDbAuthSource;

        this.redisEnabled = newRedisEnabled;
        this.redisHost = newRedisHost;
        this.redisPort = newRedisPort;
        this.redisPassword = newRedisPassword;
        this.redisDatabase = newRedisDatabase;
        this.redisTimeout = newRedisTimeout;
        this.redisPrefix = newRedisPrefix;
        this.redisTtl = newRedisTtl;
    }

    public boolean isMenuEnabled() { return menuEnabled; }
    public int getRewardsRows() { return rewardsRows; }

    public String getDbHost() { return dbHost; }
    public int getDbPort() { return dbPort; }
    public String getDbDatabase() { return dbDatabase; }
    public String getDbUsername() { return dbUsername; }
    public String getDbPassword() { return dbPassword; }
    public boolean isDbUseSSL() { return dbUseSSL; }
    public String getDbAuthSource() { return dbAuthSource; }

    public boolean isRedisEnabled() { return redisEnabled; }
    public String getRedisHost() { return redisHost; }
    public int getRedisPort() { return redisPort; }
    public String getRedisPassword() { return redisPassword; }
    public int getRedisDatabase() { return redisDatabase; }
    public int getRedisTimeout() { return redisTimeout; }
    public String getRedisPrefix() { return redisPrefix; }
    public int getRedisTtl() { return redisTtl; }
}
