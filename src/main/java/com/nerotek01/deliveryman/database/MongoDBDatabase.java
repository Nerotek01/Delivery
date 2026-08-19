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

import java.util.Collections;
import java.util.UUID;

public class MongoDBDatabase implements Database {

    private static final String COLLECTION = "players";
    private static final String CACHE_PREFIX = "player:";

    private final Main plugin;
    private final RedisCache redis;
    private final Gson gson;
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
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    private String cacheKey(Player p) {
        return CACHE_PREFIX + p.getUniqueId();
    }

    @Override
    public void loadPlayer(final Player p) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            try {
                UUID uuid = p.getUniqueId();
                PlayerData pd = null;

                if (redis.isEnabled()) {
                    String json = redis.get(cacheKey(p));
                    if (json != null) {
                        try {
                            pd = gson.fromJson(json, PlayerData.class);
                        } catch (Exception ignored) {
                        }
                    }
                }

                if (pd == null) {
                    Document query = new Document("_id", uuid.toString());
                    Document doc = collection.find(query).first();
                    if (doc != null && doc.containsKey("data")) {
                        String json = doc.getString("data");
                        pd = gson.fromJson(json, PlayerData.class);
                        if (pd == null) {
                            pd = new PlayerData(uuid);
                        }
                    } else {
                        pd = new PlayerData(uuid);
                        createNewPlayer(p, pd);
                    }

                    if (redis.isEnabled() && pd != null) {
                        redis.setAsync(cacheKey(p), gson.toJson(pd));
                    }
                }

                plugin.getDm().addPlayer(p, pd);
                fireLoadEvent(p);
            } catch (Exception ex) {
                plugin.getLogger().warning("Failed to load player data for " + p.getName() + ": " + ex.getMessage());
            }
        }, 10L);
    }

    private void createNewPlayer(Player p, PlayerData pd) {
        Document doc = new Document("_id", p.getUniqueId().toString())
                .append("name", p.getName())
                .append("data", gson.toJson(pd));
        collection.insertOne(doc);
    }

    @Override
    public void savePlayer(final Player p) {
        final PlayerData pd = plugin.getDm().getPlayerData(p);
        if (pd == null) return;
        if (!plugin.getDm().markSaving(p.getUniqueId())) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                String json = gson.toJson(pd);
                Document query = new Document("_id", p.getUniqueId().toString());
                Document update = new Document("$set", new Document()
                        .append("name", p.getName())
                        .append("data", json));
                collection.updateOne(query, update, new UpdateOptions().upsert(true));
                if (redis.isEnabled()) {
                    redis.setAsync(cacheKey(p), json);
                }
            } catch (Exception ex) {
                plugin.getLogger().warning("Failed to save player data for " + p.getName() + ": " + ex.getMessage());
            } finally {
                plugin.getDm().unmarkSaving(p.getUniqueId());
            }
        });
    }

    @Override
    public void savePlayerSync(Player p) {
        PlayerData pd = plugin.getDm().getPlayerData(p);
        if (pd == null) return;
        try {
            String json = gson.toJson(pd);
            Document query = new Document("_id", p.getUniqueId().toString());
            Document update = new Document("$set", new Document()
                    .append("name", p.getName())
                    .append("data", json));
            collection.updateOne(query, update, new UpdateOptions().upsert(true));
            if (redis.isEnabled()) {
                redis.setAsync(cacheKey(p), json);
            }
        } catch (Exception ex) {
            plugin.getLogger().warning("Failed to save player data (sync) for " + p.getName() + ": " + ex.getMessage());
        }
    }

    @Override
    public void close() {
        if (client != null) {
            try {
                client.close();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public String backendName() {
        return redis.isEnabled() ? "MongoDB+Redis" : "MongoDB";
    }

    private void fireLoadEvent(Player p) {
        Bukkit.getScheduler().runTask(plugin, () ->
                Bukkit.getPluginManager().callEvent(new DeliveryPlayerLoadEvent(p)));
    }
}
