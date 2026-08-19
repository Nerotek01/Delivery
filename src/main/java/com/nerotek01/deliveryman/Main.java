package com.nerotek01.deliveryman;

import com.google.gson.Gson;
import com.nerotek01.deliveryman.cache.RedisCache;
import com.nerotek01.deliveryman.cmds.DeliveryManCMD;
import com.nerotek01.deliveryman.config.Settings;
import com.nerotek01.deliveryman.database.CachedDatabase;
import com.nerotek01.deliveryman.database.MongoDBDatabase;
import com.nerotek01.deliveryman.database.MySQLDatabase;
import com.nerotek01.deliveryman.database.SQLDatabase;
import com.nerotek01.deliveryman.enums.DBType;
import com.nerotek01.deliveryman.interfaces.Database;
import com.nerotek01.deliveryman.listeners.MenuListener;
import com.nerotek01.deliveryman.listeners.PlayerListener;
import com.nerotek01.deliveryman.logging.LogLevel;
import com.nerotek01.deliveryman.logging.PluginLogger;
import com.nerotek01.deliveryman.managers.AddonManager;
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
    private boolean debugMode;

    private ConfigManager cm;
    private Settings lang;
    private Settings rewards;

    private RewardsManager rm;
    private RewardMenu rem;
    private DataManager dm;
    private Database db;
    private AddonManager adm;
    private RedisCache redis;
    private PluginLogger pluginLogger;

    private BukkitTask task;

    public static Main get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        pluginLogger = new PluginLogger(this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        cm = new ConfigManager(this);

        pluginLogger.setLevel(LogLevel.fromString(getConfig().getString("logLevel", "INFO"), LogLevel.INFO));
        debugMode = getConfig().getBoolean("debugMode", false);
        pluginLogger.setDebug(debugMode);

        lang = new Settings(this, "lang", true, false);
        rewards = new Settings(this, "rewards", false, false);

        adm = new AddonManager(this);
        adm.loadAddons();

        rm = new RewardsManager(this);
        rem = new RewardMenu(this);
        dm = new DataManager();

        try {
            db = createDatabase();
        } catch (Throwable ex) {
            pluginLogger.severe("Failed to initialize database backend", ex);
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        redis = new RedisCache(this);
        redis.connect();
        if (redis.isEnabled()) {
            db = new CachedDatabase(this, db, redis);
        }
        pluginLogger.info("Database: " + db.backendName());

        var cmd = getCommand("deliveryman");
        if (cmd != null) {
            var executor = new DeliveryManCMD(this);
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
            if (db != null) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    try {
                        db.savePlayerSync(player);
                    } catch (Exception ex) {
                        if (pluginLogger != null) {
                            pluginLogger.warning("Failed to save player data during shutdown: " + player.getName(), ex);
                        }
                    }
                }
                if (dm != null) {
                    dm.clearAll();
                }
                try {
                    db.close();
                } catch (Exception ex) {
                    if (pluginLogger != null) pluginLogger.warning("Error closing database", ex);
                }
            }
            if (redis != null) {
                redis.close();
            }
        } catch (Exception ex) {
            if (pluginLogger != null) pluginLogger.severe("Error during plugin disable", ex);
        }
    }

    public void reload() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        reloadConfig();
        debugMode = getConfig().getBoolean("debugMode", false);
        if (pluginLogger != null) {
            pluginLogger.setDebug(debugMode);
            pluginLogger.setLevel(LogLevel.fromString(getConfig().getString("logLevel", "INFO"), LogLevel.INFO));
        }
        if (cm != null) {
            cm.reload();
        }
        if (redis != null) {
            redis.reload();
        }
        lang.reload();
        rewards.reload();
        rm.reload();
        adm.loadAddons();
        startRewardMenuUpdater();
        pluginLogger.info("Configuration reloaded.");
    }

    private void startRewardMenuUpdater() {
        if (cm.isInstantUpdate()) {
            task = Bukkit.getScheduler().runTaskTimer(this, rem::updateRewardMenu, 20L, 20L);
        }
    }

    private Database createDatabase() {
        DBType type = cm.getDbType();
        return switch (type) {
            case MYSQL -> new MySQLDatabase(this);
            case MONGODB -> new MongoDBDatabase(this);
            case SQL, FLATFILE -> new SQLDatabase(this);
        };
    }

    public boolean isDebugMode() { return debugMode; }
    public Settings getLang() { return lang; }
    public Settings getRewards() { return rewards; }
    public RewardsManager getRm() { return rm; }
    public RewardMenu getRem() { return rem; }
    public DataManager getDm() { return dm; }
    public ConfigManager getCm() { return cm; }
    public Database getDb() { return db; }
    public AddonManager getAdm() { return adm; }
    public RedisCache getRedis() { return redis; }
    public PluginLogger getPluginLogger() { return pluginLogger; }
    public BukkitTask getTask() { return task; }
    public Gson getGson() { return gson; }
}
