package com.nerotek01.deliveryman.listeners;

import com.nerotek01.deliveryman.Main;
import com.nerotek01.deliveryman.api.DeliveryPlayerLoadEvent;
import com.nerotek01.deliveryman.data.PlayerData;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
        PlayerData pd = plugin.getDm().getPlayerData(p.getUniqueId());
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

        TextComponent component = new TextComponent(TextComponent.fromLegacyText(formatted));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new TextComponent[]{new TextComponent("\u00a7eClick here to open the rewards menu!")}));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rewards"));
        p.spigot().sendMessage(component);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        plugin.getDb().savePlayer(p);
        plugin.getDm().removePlayer(p.getUniqueId());
    }
}
