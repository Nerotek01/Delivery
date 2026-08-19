package com.nerotek01.deliveryman.listeners;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.api.DeliveryPlayerLoadEvent;
import com.nerotek01.deliveryman.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
        if (!plugin.getCm().isMenuEnabled()) {
            return;
        }
        PlayerData pd = plugin.getDm().getPlayerData(p);
        if (pd == null) {
            return;
        }
        int rewardCount = plugin.getRm().getAvailableRewards(p, pd);

        String messageKey = rewardCount > 0 ? "messages.joinWithRewards" : "messages.joinNoRewards";
        String message = plugin.getLang().get(messageKey);
        if (message == null) return;
        String formatted = message
                .replace("<rewards>", String.valueOf(rewardCount))
                .replace("<player>", p.getName());
        p.sendMessage(formatted);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        handlePlayerLeave(e.getPlayer());
    }

    private void handlePlayerLeave(Player p) {
        plugin.getDb().savePlayer(p);
    }
}
