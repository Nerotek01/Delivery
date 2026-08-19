package com.nerotek01.deliveryman.database;

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
import com.nerotek01.deliveryman.data.PlayerData;
import com.nerotek01.deliveryman.interfaces.Database;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.UUID;

public class MongoDBDatabase implements Database {

    private static final String COLLECTION = "players";

    private final Main plugin;
    private MongoClient client;
    private MongoCollection<Document> collection;

    public MongoDBDatabase(Main plugin) {
        this.plugin = plugin;
        connect();
    }

    private void connect() {
        try {
            String host = plugin.getCm().getIp();
            int port = plugin.getCm().getPort();
            String database = plugin.getCm().getDatabase();
            String username = plugin.getCm().getUsername();
            String password = plugin.getCm().getPassword();
            String authSource = plugin.getCm().getAuthSource();
            boolean useSSL = plugin.getCm().isUseSSL();

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
            plugin.getPluginLogger().severe("MongoDB connection failed", ex);
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    @Override
    public void loadPlayer(final Player p) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            try {
                UUID uuid = p.getUniqueId();
                Document query = new Document("_id", uuid.toString());
                Document doc = collection.find(query).first();
                PlayerData pd;
                if (doc != null && doc.containsKey("data")) {
                    String json = doc.getString("data");
                    pd = plugin.getGson().fromJson(json, PlayerData.class);
                    if (pd == null) {
                        pd = new PlayerData(uuid);
                    }
                } else {
                    pd = new PlayerData(uuid);
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
            } catch (Exception ex) {
                plugin.getPluginLogger().warning("Failed to load player data for " + p.getName(), ex);
            }
        }, 10L);
    }

    private void createNewPlayer(Player p, PlayerData pd) {
        Document doc = new Document("_id", p.getUniqueId().toString())
                .append("name", p.getName())
                .append("data", plugin.getGson().toJson(pd));
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
                Document query = new Document("_id", p.getUniqueId().toString());
                Document update = new Document("$set", new Document()
                        .append("name", p.getName())
                        .append("data", plugin.getGson().toJson(pd)));
                collection.updateOne(query, update, new UpdateOptions().upsert(true));
            } catch (Exception ex) {
                plugin.getPluginLogger().warning("Failed to save player data for " + p.getName(), ex);
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
            Document query = new Document("_id", p.getUniqueId().toString());
            Document update = new Document("$set", new Document()
                    .append("name", p.getName())
                    .append("data", plugin.getGson().toJson(pd)));
            collection.updateOne(query, update, new UpdateOptions().upsert(true));
        } catch (Exception ex) {
            plugin.getPluginLogger().warning("Failed to save player data (sync) for " + p.getName(), ex);
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
        return "MongoDB";
    }
}
