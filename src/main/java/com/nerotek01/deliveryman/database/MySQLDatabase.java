package com.nerotek01.deliveryman.database;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.data.PlayerData;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

public class MySQLDatabase implements Database {
    private final Main plugin;
    private final HikariDataSource dataSource;

    public MySQLDatabase(Main plugin) {
        this.plugin = plugin;
        this.dataSource = setupDataSource();
        createTable();
    }

    private HikariDataSource setupDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(buildJdbcUrl());
        config.setDriverClassName("com.mysql.jdbc.Driver");

        // Connection pool settings
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setMaxLifetime(60000);
        config.setConnectionTimeout(10000);
        config.setPoolName("DeliveryMan-" + UUID.randomUUID());

        // Performance optimizations
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("characterEncoding", "utf8");
        config.addDataSourceProperty("useSSL", "false");

        return new HikariDataSource(config);
    }

    private String buildJdbcUrl() {
        return String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s",
                plugin.getCm().getIp(),
                plugin.getCm().getPort(),
                plugin.getCm().getDatabase(),
                plugin.getCm().getUsername(),
                plugin.getCm().getPassword());
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS DeliveryMan (" +
                "UUID VARCHAR(36) PRIMARY KEY, " +
                "Name VARCHAR(36), " +
                "Data TEXT)";

        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to create table", e);
        }
    }

    @Override
    public void loadPlayer(Player p) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            String sql = "SELECT Data FROM DeliveryMan WHERE UUID = ?";

            try (Connection con = dataSource.getConnection();
                 PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, p.getUniqueId().toString());
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
        String sql = "INSERT INTO DeliveryMan(UUID, Name, Data) VALUES(?,?,?)";
        PlayerData pd = new PlayerData(p.getUniqueId());
        String jsonData = plugin.getGson().toJson(pd);

        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

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
            String sql = "UPDATE DeliveryMan SET Data = ? WHERE UUID = ?";
            String jsonData = plugin.getGson().toJson(pd);

            try (Connection con = dataSource.getConnection();
                 PreparedStatement stmt = con.prepareStatement(sql)) {

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

    @Override
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}