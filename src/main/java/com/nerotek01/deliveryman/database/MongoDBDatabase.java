package com.nerotek01.deliveryman.database;

import com.google.gson.Gson;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.api.DeliveryPlayerLoadEvent;
import com.nerotek01.deliveryman.cache.RedisCache;
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.interfaces.Database;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.IllegalPluginAccessException;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MongoDBDatabase implements Database {

    private static final String COLLECTION = "players";
    private static final String CACHE_PREFIX = "player:";

    private record PendingSave(String name, PlayerData data) {
    }

    private final Main plugin;
    private final RedisCache redis;
    private final Gson gson;
    private final Map<UUID, PendingSave> pendingSaves = new ConcurrentHashMap<>();
    private final Set<UUID> flushing = ConcurrentHashMap.newKeySet();
    private MongoClient client;
    private MongoCollection<Document> collection;

    public MongoDBDatabase(Main plugin, RedisCache redis) {
        this.plugin = plugin;
        this.redis = redis;
        this.gson = plugin.getGson();
        connect();
    }

    private void connect() {
        try {
            String host = plugin.getCm().getDbHost();
            int port = plugin.getCm().getDbPort();
            String database = plugin.getCm().getDbDatabase();
            String username = plugin.getCm().getDbUsername();
            String password = plugin.getCm().getDbPassword();
            String authSource = plugin.getCm().getDbAuthSource();
            boolean useSSL = plugin.getCm().isDbUseSSL();

            MongoClientSettings.Builder builder = MongoClientSettings.builder()
                    .applyToClusterSettings(b -> b.hosts(Collections.singletonList(new ServerAddress(host, port))))
                    .applyToSslSettings(b -> b.enabled(useSSL));

            if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                builder.credential(MongoCredential.createCredential(
                        username, authSource, password.toCharArray()));
            }

            this.client = MongoClients.create(builder.build());
            MongoDatabase db = client.getDatabase(database);
            this.collection = db.getCollection(COLLECTION);

            db.runCommand(new Document("ping", 1));
        } catch (Exception ex) {
            plugin.getLogger().severe("MongoDB connection failed: " + ex.getMessage());
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    private String cacheKey(UUID uuid) {
        return CACHE_PREFIX + uuid;
    }

    @Override
    public void loadPlayer(final Player p) {
        UUID uuid = p.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PlayerData pd = null;

                if (redis.isEnabled()) {
                    String json = redis.get(cacheKey(uuid));
                    if (json != null) {
                        try {
                            pd = gson.fromJson(json, PlayerData.class);
                        } catch (Exception ignored) {
                        }
                    }
                }

                if (pd == null) {
                    Document doc = collection.find(new Document("_id", uuid.toString())).first();
                    if (doc != null && doc.containsKey("data")) {
                        try {
                            pd = gson.fromJson(doc.getString("data"), PlayerData.class);
                        } catch (Exception ignored) {
                        }
                    }
                }

                if (pd == null) {
                    pd = new PlayerData(uuid);
                    upsertPlayerRecord(p.getName(), uuid, gson.toJson(pd));
                }

                if (redis.isEnabled()) {
                    redis.set(cacheKey(uuid), gson.toJson(pd));
                }

                plugin.getDm().mergePlayer(uuid, pd);
                fireLoadEvent(p);
            } catch (Exception ex) {
                plugin.getLogger().warning("Failed to load player data for " + p.getName() + ": " + ex.getMessage());
                plugin.getDm().mergePlayer(uuid, new PlayerData(uuid));
                fireLoadEvent(p);
            }
        });
    }

    private void upsertPlayerRecord(String name, UUID uuid, String json) {
        Document query = new Document("_id", uuid.toString());
        Document update = new Document("$set", new Document()
                .append("name", name)
                .append("data", json));
        collection.updateOne(query, update, new UpdateOptions().upsert(true));
    }

    @Override
    public void savePlayer(final Player p) {
        final PlayerData pd = plugin.getDm().getPlayerData(p.getUniqueId());
        if (pd == null) return;
        pendingSaves.put(p.getUniqueId(), new PendingSave(p.getName(), pd));
        scheduleFlush(p.getUniqueId());
    }

    private void scheduleFlush(final UUID uuid) {
        if (!flushing.add(uuid)) return;
        try {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> flush(uuid));
        } catch (IllegalPluginAccessException ex) {
            flushing.remove(uuid);
            flushNow(uuid);
        }
    }

    private void flush(final UUID uuid) {
        try {
            PendingSave save;
            while ((save = pendingSaves.remove(uuid)) != null) {
                try {
                    persist(save.name(), uuid, save.data());
                } catch (Exception ex) {
                    pendingSaves.putIfAbsent(uuid, save);
                    throw ex;
                }
            }
        } catch (Exception ex) {
            plugin.getLogger().warning("Failed to save player data: " + ex.getMessage());
        } finally {
            flushing.remove(uuid);
            if (pendingSaves.containsKey(uuid)) {
                scheduleFlush(uuid);
            }
        }
    }

    private void flushNow(final UUID uuid) {
        PendingSave save = pendingSaves.remove(uuid);
        if (save == null) return;
        try {
            persist(save.name(), uuid, save.data());
        } catch (Exception ex) {
            plugin.getLogger().warning("Failed to save player data for " + save.name() + ": " + ex.getMessage());
        }
    }

    private void persist(String name, UUID uuid, PlayerData pd) {
        String json = gson.toJson(pd);
        upsertPlayerRecord(name, uuid, json);
        if (redis.isEnabled()) {
            redis.set(cacheKey(uuid), json);
        }
    }

    @Override
    public void savePlayerSync(Player p) {
        PlayerData pd = plugin.getDm().getPlayerData(p.getUniqueId());
        if (pd == null) return;
        try {
            persist(p.getName(), p.getUniqueId(), pd);
            pendingSaves.remove(p.getUniqueId());
        } catch (Exception ex) {
            plugin.getLogger().warning("Failed to save player data (sync) for " + p.getName() + ": " + ex.getMessage());
        }
    }

    @Override
    public void close() {
        drainPendingSaves();
        if (client != null) {
            try {
                client.close();
            } catch (Exception ignored) {
            }
        }
    }

    private void drainPendingSaves() {
        for (Map.Entry<UUID, PendingSave> entry : pendingSaves.entrySet()) {
            PendingSave save = entry.getValue();
            if (pendingSaves.remove(entry.getKey(), save)) {
                try {
                    persist(save.name(), entry.getKey(), save.data());
                } catch (Exception ex) {
                    plugin.getLogger().warning("Failed to flush pending save during shutdown for "
                            + save.name() + ": " + ex.getMessage());
                }
            }
        }
    }

    @Override
    public String backendName() {
        return redis.isEnabled() ? "MongoDB+Redis" : "MongoDB";
    }

    private void fireLoadEvent(Player p) {
        try {
            Bukkit.getScheduler().runTask(plugin, () ->
                    Bukkit.getPluginManager().callEvent(new DeliveryPlayerLoadEvent(p)));
        } catch (IllegalPluginAccessException ignored) {
        }
    }
}
