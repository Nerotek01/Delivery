package com.nerotek01.deliveryman.database;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.api.DeliveryPlayerLoadEvent;
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.interfaces.Database;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabase implements Database {
    private final Main plugin;
    private HikariDataSource dataSource;

    public MySQLDatabase(Main plugin) {
        this.plugin = plugin;
        this.dataSource = setupDataSource();
        if (dataSource != null) {
            createTable();
        }
    }

    private HikariDataSource setupDataSource() {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(buildJdbcUrl());
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            String username = plugin.getCm().getUsername();
            String password = plugin.getCm().getPassword();
            if (username != null && !username.isEmpty()) {
                config.setUsername(username);
            }
            if (password != null && !password.isEmpty()) {
                config.setPassword(password);
            }

            config.setMaximumPoolSize(20);
            config.setMinimumIdle(5);
            config.setMaxLifetime(60000);
            config.setConnectionTimeout(10000);
            config.setPoolName("DeliveryMan-Hikari");

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("useServerPrepStmts", "true");
            config.addDataSourceProperty("useLocalSessionState", "true");
            config.addDataSourceProperty("characterEncoding", "utf8");
            config.addDataSourceProperty("useSSL", String.valueOf(plugin.getCm().isUseSSL()));

            return new HikariDataSource(config);
        } catch (Exception ex) {
            plugin.getPluginLogger().severe("MySQL connection failed", ex);
            Bukkit.getPluginManager().disablePlugin(plugin);
            return null;
        }
    }

    private String buildJdbcUrl() {
        boolean useSSL = plugin.getCm().isUseSSL();
        String host = URLEncoder.encode(plugin.getCm().getIp(), StandardCharsets.UTF_8);
        int port = plugin.getCm().getPort();
        String database = URLEncoder.encode(plugin.getCm().getDatabase(), StandardCharsets.UTF_8);
        return String.format("jdbc:mysql://%s:%d/%s?useSSL=%s&characterEncoding=utf8&autoReconnect=true&useUnicode=true",
                host, port, database, useSSL);
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS DeliveryMan (" +
                "UUID VARCHAR(36) PRIMARY KEY, " +
                "Name VARCHAR(36), " +
                "Data LONGTEXT" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
        try (Connection con = dataSource.getConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            plugin.getPluginLogger().warning("Failed to create MySQL table", e);
        }
    }

    @Override
    public void loadPlayer(final Player p) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            String sql = "SELECT Data FROM DeliveryMan WHERE UUID = ?";
            try (Connection con = dataSource.getConnection();
                 PreparedStatement stmt = con.prepareStatement(sql)) {
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
        String sql = "INSERT INTO DeliveryMan(UUID, Name, Data) VALUES(?,?,?)";
        String jsonData = plugin.getGson().toJson(pd);
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
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
            String sql = "UPDATE DeliveryMan SET Data = ?, Name = ? WHERE UUID = ?";
            String jsonData = plugin.getGson().toJson(pd);
            try (Connection con = dataSource.getConnection();
                 PreparedStatement stmt = con.prepareStatement(sql)) {
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
        String sql = "UPDATE DeliveryMan SET Data = ?, Name = ? WHERE UUID = ?";
        String jsonData = plugin.getGson().toJson(pd);
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, jsonData);
            stmt.setString(2, p.getName());
            stmt.setString(3, p.getUniqueId().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getPluginLogger().warning("Failed to save player data (sync) for " + p.getName(), e);
        }
    }

    @Override
    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    @Override
    public String backendName() {
        return "MySQL";
    }
}
