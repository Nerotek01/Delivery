package com.nerotek01.deliveryman;

import com.google.gson.Gson;
import com.nerotek01.deliveryman.config.Settings;
import com.nerotek01.deliveryman.controllers.VersionController;
import com.nerotek01.deliveryman.database.MySQLDatabase;
import com.nerotek01.deliveryman.enums.DBType;
import com.nerotek01.deliveryman.interfaces.Database;
import com.nerotek01.deliveryman.listeners.MenuListener;
import com.nerotek01.deliveryman.listeners.PlayerListener;
import com.nerotek01.deliveryman.managers.*;
import com.nerotek01.deliveryman.menus.RewardMenu;
import com.nerotek01.deliveryman.skins.SkinCache;
import com.nerotek01.deliveryman.utils.DependUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin {

    private static Main instance;

    private final Gson gson = new Gson();
    private boolean debugMode;

    private ConfigManager cm;
    private Settings lang, rewards;

    private RewardsManager rm;
    private RewardMenu rem;
    private DataManager dm;
    private Database db;
    private NPCManager npc;
    private SkinCache sc;
    private VersionController vc;
    private AddonManager adm;

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
        new DependUtils(this).loadDepends();
        sc = new SkinCache(this);

        debugMode = getConfig().getBoolean("debugMode");
        lang = new Settings(this, "lang", true, false);
        rewards = new Settings(this, "rewards", false, false);

        vc = new VersionController(this);
        adm = new AddonManager(this);
        adm.loadAddons();

        rm = new RewardsManager(this);
        rem = new RewardMenu(this);
        dm = new DataManager();

        db = cm.getDbType().equals(DBType.MYSQL) ? new MySQLDatabase(this) : new SQLDatabase(this);

        npc = new NPCManager(this);

        getCommand("deliveryman").setExecutor((CommandExecutor) new DeliveryManCMD(this));
        Bukkit.getPluginManager().registerEvents((Listener) new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents((Listener) new MenuListener(this), this);

        startRewardMenuUpdater();
    }

    @Override
    public void onDisable() {
        if (task != null) task.cancel();
        for (Player player : Bukkit.getOnlinePlayers()) db.savePlayerSync(player);
        if (db != null) db.close();
    }

    public void reload() {
        if (task != null) task.cancel();
        reloadConfig();
        debugMode = getConfig().getBoolean("debugMode");
        lang.reload();
        rewards.reload();
        npc.reload();
        rm.reload();
        adm.loadAddons();
        startRewardMenuUpdater();
    }

    private void startRewardMenuUpdater() {
        if (cm.isInstantUpdate()) {
            task = Bukkit.getScheduler().runTaskTimerAsynchronously(this, rem::updateRewardMenu, 20L, 20L);
        }
    }

    public void sendDebugMessage(String... messages) {
        if (!debugMode) return;
        for (String msg : messages) Bukkit.getConsoleSender().sendMessage("§b[UDM Debug] §e" + msg);
    }

    public void sendLogMessage(String... messages) {
        for (String msg : messages) Bukkit.getConsoleSender().sendMessage("§c§lUltraDM §8| §e" + msg);
    }

    public boolean isDebugMode() { return debugMode; }
    public Settings getLang() { return lang; }
    public Settings getRewards() { return rewards; }
    public RewardsManager getRm() { return rm; }
    public RewardMenu getRem() { return rem; }
    public DataManager getDm() { return dm; }
    public ConfigManager getCm() { return cm; }
    public Database getDb() { return db; }
    public NPCManager getNpc() { return npc; }
    public SkinCache getSc() { return sc; }
    public VersionController getVc() { return vc; }
    public AddonManager getAdm() { return adm; }
    public BukkitTask getTask() { return task; }
    public Gson getGson() { return gson; }
}
