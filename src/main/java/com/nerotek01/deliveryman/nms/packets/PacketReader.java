package com.nerotek01.deliveryman.nms.packets;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.api.DeliveryNPCInteractEvent;
import io.netty.channel.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

public class PacketReader {

    private final HashMap<UUID, String> channels = new HashMap<>();
    private static final Class<?> craftPlayer = Utils.getOBClass("entity.CraftPlayer");

    public void inject(Player p) {
        Channel channel = getChannel(p);
        if (channel == null) return;

        String id = UUID.randomUUID().toString();
        channels.put(p.getUniqueId(), id);

        channel.pipeline().addBefore("packet_handler", id, new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
                if (packet.getClass().getSimpleName().equals("PacketPlayInUseEntity")) {
                    readPacket(p, packet);
                }
                super.channelRead(ctx, packet);
            }
        });
    }

    public void uninject(Player p) {
        Channel channel = getChannel(p);
        if (channel == null) return;

        String id = channels.remove(p.getUniqueId());
        if (id != null) {
            channel.eventLoop().submit(() -> {
                channel.pipeline().remove(id);
                return null;
            });
        }
    }

    private Channel getChannel(Player p) {
        try {
            Object handle = craftPlayer.getMethod("getHandle").invoke(p);
            Object connection = handle.getClass().getField("playerConnection").get(handle);
            Object manager = connection.getClass().getField("networkManager").get(connection);
            return (Channel) manager.getClass().getField("channel").get(manager);
        } catch (Exception e) {
            return null;
        }
    }

    private void readPacket(Player player, Object packet) {
        int id = (int) getValue(packet, "a");
        if (!Main.get().getNpc().getNpcs().containsKey(id)) return;

        String action = getValue(packet, "action").toString();

        Bukkit.getScheduler().runTask((Plugin) Main.get(), () ->
                Bukkit.getPluginManager().callEvent((Event) new DeliveryNPCInteractEvent(player, action.equalsIgnoreCase("INTERACT")))
        );
    }

    private Object getValue(Object instance, String field) {
        try {
            Field f = instance.getClass().getDeclaredField(field);
            f.setAccessible(true);
            Object result = f.get(instance);
            f.setAccessible(false);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
