package com.nerotek01.deliveryman.listeners;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.api.DeliveryNPCInteractEvent;
import com.nerotek01.deliveryman.api.DeliveryPlayerLoadEvent;
import com.nerotek01.deliveryman.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerListener implements Listener {
    private final Main plugin;
    private final Map<UUID, Long> cooldownMap = new ConcurrentHashMap<>();

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        plugin.getDb().loadPlayer(p);
        if (!plugin.getAdm().isNPCAddon()) {
            plugin.getVc().getReader().inject(p);
        }
    }

    @EventHandler
    public void onLoad(DeliveryPlayerLoadEvent e) {
        Player p = e.getPlayer();
        PlayerData pd = plugin.getDm().getPlayerData(p);
        int rewardCount = plugin.getRm().getRewards(p, pd);

        String messageKey = rewardCount > 0 ? "messages.joinWithRewards" : "messages.joinNoRewards";
        sendFormattedMessages(p, plugin.getLang().getList(messageKey),
                "<rewards>", String.valueOf(rewardCount),
                "<player>", p.getName());

        plugin.getNpc().check(p);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        handlePlayerLeave(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onKick(PlayerKickEvent e) {
        handlePlayerLeave(e.getPlayer());
    }

    private void handlePlayerLeave(Player p) {
        plugin.getDb().savePlayer(p);
        if (!plugin.getAdm().isNPCAddon()) {
            plugin.getVc().getReader().uninject(p);
        }
        plugin.getNpc().remove(p);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        long now = System.currentTimeMillis();
        if (cooldownMap.getOrDefault(p.getUniqueId(), 0L) > now) return;

        cooldownMap.put(p.getUniqueId(), now + 200);
        plugin.getNpc().check(p);
    }

    @EventHandler
    public void onInteract(DeliveryNPCInteractEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;

        Player p = (Player) e.getPlayer();
        boolean shouldOpen = (plugin.getCm().isRight() && e.isRight()) ||
                (plugin.getCm().isLeft() && !e.isRight());

        if (shouldOpen) {
            plugin.getRem().createRewardMenu(p);
        }
    }

    private void sendFormattedMessages(Player p, Iterable<String> messages, String... replacements) {
        for (String line : messages) {
            String formatted = line;
            for (int i = 0; i < replacements.length; i += 2) {
                formatted = formatted.replace(replacements[i], replacements[i+1]);
            }
            p.sendMessage(formatted.replace("&", "§"));
        }
    }
}