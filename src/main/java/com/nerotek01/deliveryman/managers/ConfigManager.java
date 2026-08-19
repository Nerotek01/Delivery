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
        boolean newInstantUpdate = plugin.getConfig().getBoolean("rewardsMenu.instantUpdate", true);
        boolean newRight = plugin.getConfig().getBoolean("interact.right", true);
        boolean newLeft = plugin.getConfig().getBoolean("interact.left", true);

        DBType newDbType;
        String rawType = plugin.getConfig().getString("database.type", "MONGODB");
        try {
            newDbType = DBType.valueOf(rawType.toUpperCase());
        } catch (IllegalArgumentException ex) {
            plugin.getLogger().warning("Invalid database.type '" + rawType + "' - defaulting to MONGODB.");
            newDbType = DBType.MONGODB;
        }

        int newRewardsRows = Math.max(1, Math.min(6, plugin.getConfig().getInt("rewardsMenu.rows", 5)));
        int newPort = plugin.getConfig().getInt("database.port", 27017);
        String newIp = plugin.getConfig().getString("database.host", "127.0.0.1");
        String newDatabase = plugin.getConfig().getString("database.database", "DeliveryMan");
        String newUsername = plugin.getConfig().getString("database.username", "");
        String newPassword = plugin.getConfig().getString("database.password", "");
        boolean newUseSSL = plugin.getConfig().getBoolean("database.useSSL", false);
        String newAuthSource = plugin.getConfig().getString("database.authSource", "admin");

        boolean newRedisEnabled = plugin.getConfig().getBoolean("redis.enabled", false);
        String newRedisHost = plugin.getConfig().getString("redis.host", "127.0.0.1");
        int newRedisPort = plugin.getConfig().getInt("redis.port", 6379);
        String newRedisPassword = plugin.getConfig().getString("redis.password", "");
        int newRedisDatabase = plugin.getConfig().getInt("redis.database", 0);
        int newRedisTimeout = plugin.getConfig().getInt("redis.timeout", 5000);
        String newRedisPrefix = plugin.getConfig().getString("redis.prefix", "deliveryman:");
        int newRedisTtl = plugin.getConfig().getInt("redis.ttl", 3600);

        this.instantUpdate = newInstantUpdate;
        this.right = newRight;
        this.left = newLeft;
        this.dbType = newDbType;
        this.rewardsRows = newRewardsRows;
        this.port = newPort;
        this.ip = newIp;
        this.database = newDatabase;
        this.username = newUsername;
        this.password = newPassword;
        this.useSSL = newUseSSL;
        this.authSource = newAuthSource;

        this.redisEnabled = newRedisEnabled;
        this.redisHost = newRedisHost;
        this.redisPort = newRedisPort;
        this.redisPassword = newRedisPassword;
        this.redisDatabase = newRedisDatabase;
        this.redisTimeout = newRedisTimeout;
        this.redisPrefix = newRedisPrefix;
        this.redisTtl = newRedisTtl;
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
