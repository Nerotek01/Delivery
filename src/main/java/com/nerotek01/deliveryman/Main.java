package com.nerotek01.deliveryman;

import com.google.gson.Gson;
import com.nerotek01.deliveryman.cache.RedisCache;
import com.nerotek01.deliveryman.cmds.RewardsCMD;
import com.nerotek01.deliveryman.config.Settings;
import com.nerotek01.deliveryman.database.MongoDBDatabase;
import com.nerotek01.deliveryman.interfaces.Database;
import com.nerotek01.deliveryman.listeners.MenuListener;
import com.nerotek01.deliveryman.listeners.PlayerListener;
import com.nerotek01.deliveryman.managers.ConfigManager;
import com.nerotek01.deliveryman.managers.DataManager;
import com.nerotek01.deliveryman.managers.RewardsManager;
import com.nerotek01.deliveryman.menus.RewardMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin {

    private static Main instance;

    private final Gson gson = new Gson();

    private ConfigManager cm;
    private Settings lang;
    private Settings rewardsFile;

    private RewardsManager rm;
    private RewardMenu rem;
    private DataManager dm;
    private Database db;
    private RedisCache redis;

    private BukkitTask task;

    public static Main get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        cm = new ConfigManager(this);

        lang = new Settings(this, "lang", true, false);
        rewardsFile = new Settings(this, "rewards", false, false);

        rm = new RewardsManager(this);
        rem = new RewardMenu(this);
        dm = new DataManager();

        try {
            redis = new RedisCache(this);
            redis.connect();
            db = new MongoDBDatabase(this, redis);
            getLogger().info("Database: " + db.backendName());
        } catch (Throwable ex) {
            getLogger().severe("Failed to initialize database backend: " + ex.getMessage());
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        var cmd = getCommand("rewards");
        if (cmd != null) {
            var executor = new RewardsCMD(this);
            cmd.setExecutor(executor);
            cmd.setTabCompleter(executor);
        }

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(this), this);

        startRewardMenuUpdater();
    }

    @Override
    public void onDisable() {
        try {
            if (task != null) {
                task.cancel();
                task = null;
            }
            if (rem != null) {
                rem.clear();
            }
            if (db != null) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    try {
                        db.savePlayerSync(player);
                    } catch (Exception ex) {
                        getLogger().warning("Failed to save player data during shutdown: " + player.getName() + ": " + ex.getMessage());
                    }
                }
                if (dm != null) {
                    dm.clearAll();
                }
                try {
                    db.close();
                } catch (Exception ex) {
                    getLogger().warning("Error closing database: " + ex.getMessage());
                }
            }
            if (redis != null) {
                redis.close();
            }
        } catch (Exception ex) {
            getLogger().severe("Error during plugin disable: " + ex.getMessage());
        }
    }

    public void reload() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        reloadConfig();
        if (cm != null) {
            cm.reload();
        }
        if (redis != null) {
            redis.reload();
        }
        lang.reload();
        rewardsFile.reload();
        rm.reload();
        if (rem != null) {
            rem.clear();
        }
        startRewardMenuUpdater();
        getLogger().info("Configuration reloaded.");
    }

    private void startRewardMenuUpdater() {
        task = Bukkit.getScheduler().runTaskTimer(this, rem::updateRewardMenu, 20L, 20L);
    }

    public Settings getLang() { return lang; }
    public Settings getRewards() { return rewardsFile; }
    public RewardsManager getRm() { return rm; }
    public RewardMenu getRem() { return rem; }
    public DataManager getDm() { return dm; }
    public ConfigManager getCm() { return cm; }
    public Database getDb() { return db; }
    public RedisCache getRedis() { return redis; }
    public Gson getGson() { return gson; }
}
