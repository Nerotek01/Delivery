package com.nerotek01.deliveryman.nms;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.skins.SkinProperty;
import com.nerotek01.deliveryman.utils.Reflections;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.UUID;

public class NPC_v1_8_R3 extends NPC {

    private int entityID;
    private GameProfile gameProfile;
    private EntityPlayer npcEntity;
    private PacketPlayOutNamedEntitySpawn spawnPacket;
    private PacketPlayOutEntityHeadRotation rotationPacket;
    private PacketPlayOutPlayerInfo addTab, removeTab;
    private PacketPlayOutScoreboardTeam scoreboardTeam;

    private final MinecraftServer nmsServer;
    private final WorldServer nmsWorld;

    public NPC_v1_8_R3(Location location, SkinProperty sp) {
        super(location, sp);
        this.nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        this.nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        create();
    }

    private void create() {
        this.entityID = (int) (Math.random() * 1000) + 2000;
        this.gameProfile = new GameProfile(UUID.randomUUID(), "§8[NPC] " + UUID.randomUUID().toString().substring(0, 8));
        this.gameProfile.getProperties().put("textures", new Property(sp.getName(), sp.getValue(), sp.getSignature()));

        this.npcEntity = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
        this.npcEntity.setLocation(location.getX(), location.getY(), location.getZ(),
                newDirection(location.getYaw()), newDirection(location.getPitch()));

        this.spawnPacket = new PacketPlayOutNamedEntitySpawn(npcEntity);
        this.rotationPacket = new PacketPlayOutEntityHeadRotation(npcEntity, newDirection(location.getYaw()));

        // Reflections
        Reflections.setValue(spawnPacket, "a", entityID);
        Reflections.setValue(spawnPacket, "b", gameProfile.getId());

        // Tab packets
        this.addTab = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npcEntity);
        this.removeTab = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npcEntity);

        // Scoreboard
        this.scoreboardTeam = new PacketPlayOutScoreboardTeam();
        try {
            Field f = scoreboardTeam.getClass().getDeclaredField("g");
            f.setAccessible(true);
            ((Collection<String>) f.get(scoreboardTeam)).add(gameProfile.getName());
        } catch (Exception ignored) {}
    }

    private byte newDirection(float loc) {
        return (byte) ((int) (loc * 256.0F / 360.0F));
    }

    public int getEntityID() {
        return entityID;
    }

    public void spawn(Player p) {
        sendPacket(addTab, p);
        sendPacket(spawnPacket, p);
        sendPacket(rotationPacket, p);

        new BukkitRunnable() {
            @Override
            public void run() {
                sendPacket(removeTab, p);
            }
        }.runTaskLater((Plugin) Main.get(), 3L);
    }

    public void hide(Player p) {
        sendPacket(new PacketPlayOutEntityDestroy(entityID), p);
    }

    private void sendPacket(Packet<?> packet, Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
