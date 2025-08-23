package com.nerotek01.deliveryman.managers;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.enums.DBType;

import java.util.UUID;

public class ConfigManager {
    private final Main plugin;
    private int rewardsRows, port;
    private String ip, database, username, password;
    private DBType dbType;
    private boolean instantUpdate, right, left;
    private UUID deliverySkin;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        instantUpdate = plugin.getConfig().getBoolean("rewardsMenu.instantUpdate");
        right = plugin.getConfig().getBoolean("interact.right");
        left = plugin.getConfig().getBoolean("interact.left");
        deliverySkin = UUID.fromString(plugin.getConfig().getString("deliverySkin"));
        dbType = DBType.valueOf(plugin.getConfig().getString("database.type").toUpperCase());
        rewardsRows = plugin.getConfig().getInt("rewardsMenu.rows");
        port = plugin.getConfig().getInt("database.port");
        ip = plugin.getConfig().getString("database.host");
        database = plugin.getConfig().getString("database.database");
        username = plugin.getConfig().getString("database.username");
        password = plugin.getConfig().getString("database.password");
    }

    // Getter methods
    public int getRewardsRows() { return rewardsRows; }
    public int getPort() { return port; }
    public String getIp() { return ip; }
    public String getDatabase() { return database; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public DBType getDbType() { return dbType; }
    public boolean isInstantUpdate() { return instantUpdate; }
    public boolean isRight() { return right; }
    public boolean isLeft() { return left; }
    public UUID getDeliverySkin() { return deliverySkin; }
}