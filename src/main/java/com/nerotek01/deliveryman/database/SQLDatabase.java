package com.nerotek01.deliveryman.database;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.api.DeliveryPlayerLoadEvent;
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.interfaces.Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantLock;

public class SQLDatabase implements Database {
    private final Main plugin;
    private final ReentrantLock connectionLock = new ReentrantLock();
    private Connection connection;
    private final String dbFile;

    public SQLDatabase(Main plugin) {
        this.plugin = plugin;
        this.dbFile = plugin.getDataFolder() + "/DeliveryMan.db";
        connect();
    }

    private void connect() {
        try {
            File dataFile = new File(dbFile);
            File parent = dataFile.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();
            if (!dataFile.exists() && !dataFile.createNewFile()) {
                throw new RuntimeException("Failed to create database file");
            }
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            createTable();
        } catch (Exception e) {
            plugin.getPluginLogger().severe("SQLite connection failed", e);
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS DeliveryMan(UUID TEXT PRIMARY KEY, Name TEXT, Data TEXT)";
        executeUpdate(sql);
    }

    @Override
    public void loadPlayer(final Player p) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            String selectSQL = "SELECT Data FROM DeliveryMan WHERE UUID = ?";
            try (Connection con = getConnection();
                 PreparedStatement stmt = con.prepareStatement(selectSQL)) {
                stmt.setString(1, p.getUniqueId().toString());
                ResultSet rs = stmt.executeQuery();
                PlayerData pd;
                if (rs.next()) {
                    pd = plugin.getGson().fromJson(rs.getString("Data"), PlayerData.class);
                    if (pd == null) {
                        pd = new PlayerData(p.getUniqueId());
                    }
                } else {
                    pd = new PlayerData(p.getUniqueId());
                    createNewPlayer(p, pd);
                }
                plugin.getDm().addPlayer(p, pd);
                final PlayerData loaded = pd;
                if (plugin.getDb() instanceof CachedDatabase) {
                    ((CachedDatabase) plugin.getDb()).onBackendLoaded(p, loaded);
                } else {
                    Bukkit.getScheduler().runTask(plugin, () ->
                            Bukkit.getPluginManager().callEvent(new DeliveryPlayerLoadEvent(p)));
                }
            } catch (SQLException e) {
                plugin.getPluginLogger().warning("Failed to load player data for " + p.getName(), e);
            }
        }, 10L);
    }

    private void createNewPlayer(Player p, PlayerData pd) throws SQLException {
        String insertSQL = "INSERT INTO DeliveryMan(UUID, Name, Data) VALUES(?,?,?)";
        String jsonData = plugin.getGson().toJson(pd);
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(insertSQL)) {
            stmt.setString(1, p.getUniqueId().toString());
            stmt.setString(2, p.getName());
            stmt.setString(3, jsonData);
            stmt.executeUpdate();
        }
    }

    @Override
    public void savePlayer(final Player p) {
        final PlayerData pd = plugin.getDm().getPlayerData(p);
        if (pd == null) return;
        if (!plugin.getDm().markSaving(p.getUniqueId())) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String updateSQL = "UPDATE DeliveryMan SET Data = ?, Name = ? WHERE UUID = ?";
            String jsonData = plugin.getGson().toJson(pd);
            try (Connection con = getConnection();
                 PreparedStatement stmt = con.prepareStatement(updateSQL)) {
                stmt.setString(1, jsonData);
                stmt.setString(2, p.getName());
                stmt.setString(3, p.getUniqueId().toString());
                stmt.executeUpdate();
            } catch (SQLException e) {
                plugin.getPluginLogger().warning("Failed to save player data for " + p.getName(), e);
            } finally {
                plugin.getDm().unmarkSaving(p.getUniqueId());
            }
        });
    }

    @Override
    public void savePlayerSync(Player p) {
        PlayerData pd = plugin.getDm().getPlayerData(p);
        if (pd == null) return;
        String updateSQL = "UPDATE DeliveryMan SET Data = ?, Name = ? WHERE UUID = ?";
        String jsonData = plugin.getGson().toJson(pd);
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(updateSQL)) {
            stmt.setString(1, jsonData);
            stmt.setString(2, p.getName());
            stmt.setString(3, p.getUniqueId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getPluginLogger().warning("Failed to save player data (sync) for " + p.getName(), e);
        }
    }

    private void executeUpdate(String sql) {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            plugin.getPluginLogger().warning("Failed to execute update: " + sql, e);
        }
    }

    private Connection getConnection() throws SQLException {
        connectionLock.lock();
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    Class.forName("org.sqlite.JDBC");
                    connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
                } catch (ClassNotFoundException ex) {
                    throw new SQLException("SQLite driver not available", ex);
                }
            }
            return connection;
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public void close() {
        connectionLock.lock();
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getPluginLogger().warning("Failed to close SQLite connection", e);
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public String backendName() {
        return "SQLite";
    }
}
