package com.nerotek01.deliveryman.listeners;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.api.DeliveryPlayerLoadEvent;
import com.nerotek01.deliveryman.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        plugin.getDb().loadPlayer(e.getPlayer());
    }

    @EventHandler
    public void onLoad(DeliveryPlayerLoadEvent e) {
        Player p = e.getPlayer();
        PlayerData pd = plugin.getDm().getPlayerData(p);
        int rewardCount = plugin.getRm().getAvailableRewards(p, pd);

        String messageKey = rewardCount > 0 ? "messages.joinWithRewards" : "messages.joinNoRewards";
        sendFormattedMessages(p, plugin.getLang().getList(messageKey),
                "<rewards>", String.valueOf(rewardCount),
                "<player>", p.getName());
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
    }

    private void sendFormattedMessages(Player p, Iterable<String> messages, String... replacements) {
        if (messages == null) return;
        for (String line : messages) {
            if (line == null) continue;
            String formatted = line;
            for (int i = 0; i + 1 < replacements.length; i += 2) {
                formatted = formatted.replace(replacements[i], replacements[i + 1]);
            }
            p.sendMessage(formatted.replace("&", "\u00a7"));
        }
    }
}
