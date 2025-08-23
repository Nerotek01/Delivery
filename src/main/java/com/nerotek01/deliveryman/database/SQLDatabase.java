package com.nerotek01.deliveryman.database;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.io.File;
import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

public class SQLDatabase implements Database {
    private final Main plugin;
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
            if (!dataFile.exists() && !dataFile.createNewFile()) {
                throw new RuntimeException("Failed to create database file");
            }

            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            plugin.getLogger().info("SQLite connected successfully");
            createTable();
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Database connection failed", e);
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS DeliveryMan(UUID TEXT PRIMARY KEY, Name TEXT, Data TEXT)";
        executeUpdate(sql);
    }

    @Override
    public void loadPlayer(Player p) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            UUID uuid = p.getUniqueId();
            String selectSQL = "SELECT Data FROM DeliveryMan WHERE UUID = ?";

            try (Connection con = getConnection();
                 PreparedStatement stmt = con.prepareStatement(selectSQL)) {

                stmt.setString(1, uuid.toString());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    PlayerData pd = plugin.getGson().fromJson(rs.getString("Data"), PlayerData.class);
                    plugin.getDm().addPlayer(p, pd);
                } else {
                    createNewPlayer(p);
                }
            } catch (SQLException e) {
                plugin.getLogger().log(Level.WARNING, "Failed to load player data", e);
            }
        }, 10L);
    }

    private void createNewPlayer(Player p) throws SQLException {
        String insertSQL = "INSERT INTO DeliveryMan(UUID, Name, Data) VALUES(?,?,?)";
        PlayerData pd = new PlayerData(p.getUniqueId());
        String jsonData = plugin.getGson().toJson(pd);

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(insertSQL)) {

            stmt.setString(1, p.getUniqueId().toString());
            stmt.setString(2, p.getName());
            stmt.setString(3, jsonData);
            stmt.executeUpdate();

            plugin.getDm().addPlayer(p, pd);
        }
    }

    @Override
    public void savePlayer(Player p) {
        PlayerData pd = plugin.getDm().getPlayerData(p);
        if (pd == null) return;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String updateSQL = "UPDATE DeliveryMan SET Data = ? WHERE UUID = ?";
            String jsonData = plugin.getGson().toJson(pd);

            try (Connection con = getConnection();
                 PreparedStatement stmt = con.prepareStatement(updateSQL)) {

                stmt.setString(1, jsonData);
                stmt.setString(2, p.getUniqueId().toString());
                stmt.executeUpdate();
            } catch (SQLException e) {
                plugin.getLogger().log(Level.WARNING, "Failed to save player data", e);
            } finally {
                plugin.getDm().removePlayer(p);
            }
        });
    }

    // Other methods remain similar but use try-with-resources
    private void executeUpdate(String sql) {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            plugin.getLogger().log(Level.WARNING, "Failed to execute update", e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getLogger().log(Level.WARNING, "Failed to close connection", e);
        }
    }
}